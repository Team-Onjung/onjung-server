package com.onjung.onjung;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@EnableBatchProcessing
@SpringBootApplication
public class OnjungApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnjungApplication.class, args);
	}

}
