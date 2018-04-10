package com.qianfeng.quarze;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by admin on 2018/4/10.
 */
public class HelloQuarze implements Job {
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

          //创建工程的详情
        JobDetail Detail = jobExecutionContext.getJobDetail();
        String zhagbin = Detail.getJobDataMap().getString("zhagbin");
        String panchong = Detail.getJobDataMap().getString("panchong");
        System.out.println(zhagbin+"-->"+panchong);

    }

    public static void main(String[] args) {
        //创建一个scheduler，执行计划
        Scheduler scheduler = null;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();

        //定义一个Trigger,触发条件类
        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .repeatForever())
                .build();
        JobDetail build = JobBuilder.newJob(HelloQuarze.class)
                .withIdentity("zhagbin", "trigger1") //??name/group
                .usingJobData("panchong", "quartz") //????
                .build();
        //加入调度
        scheduler.scheduleJob(build,trigger);
        //启动调度

        scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
