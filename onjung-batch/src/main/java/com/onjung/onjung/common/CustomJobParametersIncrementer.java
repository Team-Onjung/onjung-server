//package com.onjung.onjung.common;
//
//import org.springframework.batch.core.JobParameters;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.core.JobParametersIncrementer;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.UUID;
//
//public class CustomJobParametersIncrementer implements JobParametersIncrementer {
//    /**
//     * UUID 를 incrementer 로 지정해보자.
//     *
//     * @param jobParameters
//     * @return
//     */
//    @Override
//    public JobParameters getNext(JobParameters jobParameters) {
//        // 해당 UUID으로 설정
//        UUID id = UUID.randomUUID();
//
//        return new JobParametersBuilder().addString("run.id", id.toString()).toJobParameters();
//    }
//}
