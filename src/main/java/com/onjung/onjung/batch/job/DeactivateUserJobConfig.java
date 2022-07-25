package com.onjung.onjung.batch.job;

import com.onjung.onjung.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DeactivateUserJobConfig {
    @Autowired public JobBuilderFactory jobBuilderFactory;
    @Autowired public StepBuilderFactory stepBuilderFactory;
    @Autowired public EntityManagerFactory entityManagerFactory;

    public static final String JOB_NAME = "DEACTIVATE_USER_JOB";
    public static final String STEP_NAME = "DEACTIVATE_USER_STEP";

    @Bean(name = JOB_NAME)
    public Job deactivateUserJob() throws Exception {

        Job deactivateUserJob = jobBuilderFactory.get(JOB_NAME)
                .start(deactivateUserStep())
                .incrementer(new RunIdIncrementer())
                .build();

        return deactivateUserJob;
    }

    @Bean(name = STEP_NAME)
    @JobScope
    public Step deactivateUserStep() throws Exception {
        return stepBuilderFactory.get(STEP_NAME)
                .<User,User>chunk(10)
                .reader(deactivateUserReader())
                .processor(deactivateUserProcessor())
                .writer(deactivateUserWriter())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<User> deactivateUserReader() throws Exception {

        Map<String,Object> parameterValues = new HashMap<>();

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
    public ItemProcessor<User, User> deactivateUserProcessor(){
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
    public JpaItemWriter<User> deactivateUserWriter(){
        return new JpaItemWriterBuilder<User>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
