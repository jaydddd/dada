package com.qianfeng.quarze;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by admin on 2018/4/10.
 */
public class MyJob  implements Job{

     private static  int number=1;
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {


        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        String name = jobDetail.getJobDataMap().getString("name");
        System.out.println("开始"+number+++"计时"+"姓名是-》》》"+name);

    }
}
