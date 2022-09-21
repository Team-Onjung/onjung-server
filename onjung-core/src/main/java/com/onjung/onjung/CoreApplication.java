package com.onjung.onjung;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableCaching
@EnableScheduling
@EnableAsync
@EnableAutoConfiguration
@SpringBootApplication
@EnableEncryptableProperties
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);

//		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

//		textEncryptor.setPassword("onjung");

//		String url = textEncryptor.encrypt();
//		String username = textEncryptor.encrypt("");
//		String password = textEncryptor.encrypt("");
//		System.out.println("url = " + url);
//		System.out.println("username:"+username);
//		System.out.println("password:"+password);
	}

}
