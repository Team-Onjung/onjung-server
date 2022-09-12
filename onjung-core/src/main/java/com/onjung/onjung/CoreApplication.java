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

		BasicTextEncryptor textEncryptor = new BasicTextEncryptor();

		textEncryptor.setPassword("onjung");

		String url = textEncryptor.encrypt("jdbc:mysql://iu3eb0ys9fgqd8g9:f74a4eemkjrvmdqa@migae5o25m2psr4q.cbetxkdyhwsb.us-east-1.rds.amazonaws.com:3306/jms72evh5023yfsc?allowPublicKeyRetrieval=true&useSSL=false&useUnicode=true&serverTimezone=UTC");
		String username = textEncryptor.encrypt("iu3eb0ys9fgqd8g9");
		String password = textEncryptor.encrypt("w15k1n1w5deklhkh");
		System.out.println("url = " + url);
		System.out.println("username:"+username);
		System.out.println("password:"+password);
	}

}
