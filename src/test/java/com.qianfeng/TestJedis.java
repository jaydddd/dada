package com.qianfeng;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by admin on 2018/4/10.
 */
public class TestJedis {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(20);
        Jedis jedis = new Jedis("192.168.72.188");

        jedis.set("count", "5"); // 重置库存
        jedis.del("succesuse", "failuse");// 清空抢成功的
        for (int i = 0; i <1000; i++) {
            executor.execute(new MyRunnable1("count"));
        }
        executor.shutdown();
    }
}

class MyRunnable1 implements Runnable {
    private Jedis jedis = new Jedis("192.168.72.188", 6379);
    private String key;

    public MyRunnable1(String key) {
        this.key = key;
    }


    public void run() {
        try {
            jedis.watch(key);// watch key
            int count = Integer.valueOf(jedis.get(key));
            String useId = UUID.randomUUID().toString();
            if(count<=0){
                jedis.unwatch();  //数量不够直接  unwatch key
                jedis.sadd("failuse", useId);
                System.err.println("用户ooooooooooooooooooo"+useId+"抢购失败！！！");
            }else{

                Transaction tx = jedis.multi();// 开启事务

                tx.incrBy(key, -1); //减少库存

                List<Object> list= tx.exec();// 提交事务，如果此时watch key被改动了，则返回null

                if (list.size()>0) {  //多个进程同时 key>0  key相等时
                    jedis.sadd("succesuse", useId);
                    //  抢购成功业务逻辑
                    System.out.println("用户"+ Thread.currentThread().getName()+"--->"+useId+"抢购成功！！！");
                } else {
                    // 抢购失败业务逻辑
                    jedis.sadd("failuse", useId);
                    System.err.println("用户"+useId+ Thread.currentThread().getName()+"--->"+"抢购失败！！！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jedis.close();
        }
    }
}
