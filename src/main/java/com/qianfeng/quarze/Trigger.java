package com.qianfeng.quarze;

import org.quartz.DailyTimeIntervalScheduleBuilder;
import org.quartz.DailyTimeIntervalTrigger;
import org.quartz.TimeOfDay;
import org.quartz.spi.MutableTrigger;

/**
 * Created by admin on 2018/4/10.
 */
public class Trigger {


    public static void main(String[] args) {
        MutableTrigger build = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                .startingDailyAt(TimeOfDay.hourAndMinuteOfDay(9, 0)) //??9?00

                .endingDailyAt(TimeOfDay.hourAndMinuteOfDay(18, 0)) //18?00 ?
                .onDaysOfTheWeek(5, 4, 3, 2, 1) //?

                .withIntervalInHours(1) //???1??????
                .withRepeatCount(100) //????100??????100+1??
                .build();

    }
}
