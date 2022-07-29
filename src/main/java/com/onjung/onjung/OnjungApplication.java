package com.onjung.onjung;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableCaching
@EnableBatchProcessing
@EnableScheduling
@SpringBootApplication
@EnableAsync
@EnableJpaRepositories(basePackages = {"com.onjung.onjung.batch",
										"com.onjung.onjung.feed.repository.jpa",
										"com.onjung.onjung.item",
										"com.onjung.onjung.user"})
@EnableR2dbcRepositories(basePackages = "com.onjung.onjung.feed.repository.r2dbc")
public class OnjungApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnjungApplication.class, args);
	}

}
