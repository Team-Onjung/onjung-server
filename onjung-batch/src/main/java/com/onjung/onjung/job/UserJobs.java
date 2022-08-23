package com.onjung.onjung.job;

import com.onjung.onjung.common.CustomJobParametersIncrementer;
import com.onjung.onjung.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.launch.support.RunIdIncrementer;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class UserJobs {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public EntityManagerFactory entityManagerFactory;

    public static final String JOB_NAME = "USER_JOBS";
    public static final String STEP_NAME1 = "DEACTIVATE_USER_STEP";
    public static final String STEP_NAME2 = "BLOCK_USER_STEP";

    @Bean(name = JOB_NAME)
    public Job parallelUserJobs() throws Exception {

        Flow deactivateUser = new FlowBuilder<Flow>("deactivateUser")
                .from(deactivateUserStep())
                .on(STEP_NAME1)
                .end()
                .build();

        Flow blockUserUser = new FlowBuilder<Flow>("blockUser")
                .from(blockUserStep())
                .on(STEP_NAME2)
                .end()
                .build();

        Flow parallelUserSteps = new FlowBuilder<Flow>("parallelUserSteps")
                .split(new SimpleAsyncTaskExecutor())
                .add(deactivateUser, blockUserUser)
                .build();

        return jobBuilderFactory.get(JOB_NAME)
                .start(parallelUserSteps)
                .end()
                .incrementer(new CustomJobParametersIncrementer())
                .build();
    }

    @Bean(name = STEP_NAME1)
    public Step deactivateUserStep() throws Exception {
        return stepBuilderFactory.get(STEP_NAME1)
                .<User, User>chunk(10)
                .reader(deactivateUserReader())
                .processor(deactivateUserProcessor())
                .writer(deactivateUserWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<User> deactivateUserReader() throws Exception {

        Map<String, Object> parameterValues = new HashMap<>();

        LocalDateTime oneYearAgo = LocalDateTime.now();

        parameterValues.put("date", oneYearAgo);

        return new JpaPagingItemReaderBuilder<User>()
                .pageSize(10)
                .queryString("SELECT u FROM User as u WHERE u.isActive=true and DATEDIFF(u.lastLogined, :date) > 365")
                .parameterValues(parameterValues)
                .entityManagerFactory(entityManagerFactory)
                .name("JpaPagingItemReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<User, User> deactivateUserProcessor() {
        return new ItemProcessor<User, User>() {

            @Override
            public User process(User user) throws Exception {

                user.changeIsActive();
                return user;
            }
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<User> deactivateUserWriter() {
        return new JpaItemWriterBuilder<User>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }

    //

    @Bean(name = STEP_NAME2)
    public Step blockUserStep() throws Exception {
        return stepBuilderFactory.get(STEP_NAME2)
                .<User, User>chunk(10)
                .reader(blockUserReader())
                .processor(blockUserProcessor())
                .writer(blockUserWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<User> blockUserReader() throws Exception {

        return new JpaPagingItemReaderBuilder<User>()
                .pageSize(10)
                .queryString("SELECT u FROM User as u WHERE u.isActive=true and u.reportCnt > 10")
                .entityManagerFactory(entityManagerFactory)
                .name("JpaPagingItemReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<User, User> blockUserProcessor() {
        return new ItemProcessor<User, User>() {

            @Override
            public User process(User user) throws Exception {

                user.changeIsBlocked();
                return user;
            }
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<User> blockUserWriter() {
        return new JpaItemWriterBuilder<User>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
