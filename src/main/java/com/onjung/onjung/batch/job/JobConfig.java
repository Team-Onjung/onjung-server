package com.onjung.onjung.batch.job;

import com.onjung.onjung.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Member;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
@EnableBatchProcessing
public class JobConfig {
    @Autowired public JobBuilderFactory jobBuilderFactory;
    @Autowired public StepBuilderFactory stepBuilderFactory;
    @Autowired public EntityManagerFactory entityManagerFactory;

    @Bean
    public Job ExampleJob() throws Exception {

        Job exampleJob = jobBuilderFactory.get("exampleJob")
                .start(Step())
                .build();

        return exampleJob;
    }

    @Bean
    @JobScope
    public Step Step() throws Exception {
        return stepBuilderFactory.get("Step")
                .<User,User>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<User> reader() throws Exception {

        Map<String,Object> parameterValues = new HashMap<>();

        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1L);

        parameterValues.put("date", oneYearAgo);

        return new JpaPagingItemReaderBuilder<User>()
                .pageSize(10)
                .parameterValues(parameterValues)
                .queryString("SELECT p FROM User as p WHERE DATEDIFF(p.lastLogined,date) >= 0")
                .entityManagerFactory(entityManagerFactory)
                .name("JpaPagingItemReader")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<User, User> processor(){

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
    public JpaItemWriter<User> writer(){
        return new JpaItemWriterBuilder<User>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
