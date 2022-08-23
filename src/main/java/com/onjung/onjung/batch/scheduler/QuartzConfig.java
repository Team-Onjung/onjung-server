//package com.onjung.onjung.batch.scheduler;
//
//import com.onjung.onjung.batch.job.UserJobExecutor;
//import org.quartz.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class QuartzConfig {
//
//    @Bean
//    public JobDetail quartzJobDetail() {
//        return JobBuilder.newJob(UserJobExecutor.class)
//                .build();
//    }
//
//    @Bean
//    public Trigger jobTrigger() {
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInHours(24).repeatForever();
//
//        return TriggerBuilder.newTrigger()
//                .forJob(quartzJobDetail())
//                .withSchedule(scheduleBuilder)
//                .build();
//    }
//}
