package com.onjung.onjung.batch.job;

import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.TriggerBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class QuartzConfig {

    @Scheduled(cron = "* * * * * *")
    public void test(){
//        System.out.println("true = " + true);

    }

}
