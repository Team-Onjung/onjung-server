//package com.onjung.onjung.common;
//
//import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
//import org.junit.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//class JasyptConfigTest {
//    @Test
//    void contextLoads() {
//    }
//
//    @Test
//    void jasypt() {
//        String url = "my_db_url";
//        String username = "my_db_username";
//        String password = "my_db_password";
//
//        System.out.println(jasyptEncoding(url));
//        System.out.println(jasyptEncoding(username));
//        System.out.println(jasyptEncoding(password));
//    }
//
//    public String jasyptEncoding(String value) {
//
//        String key = "my_jasypt_key";
//        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
//        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
//        pbeEnc.setPassword(key);
//        return pbeEnc.encrypt(value);
//    }
//
//}