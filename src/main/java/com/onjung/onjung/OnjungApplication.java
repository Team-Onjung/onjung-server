package com.onjung.onjung;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableCaching
@EnableBatchProcessing
@EnableScheduling
@SpringBootApplication
@EnableAsync
public class OnjungApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnjungApplication.class, args);
	}

}
