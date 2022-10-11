//package com.onjung.onjung.user.domain;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.time.LocalDate;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserTest {
//
//    @Test
//    @Transactional
//    void UserEntityTest() {
//        LocalDate birthDate= LocalDate.ofYearDay(2022,1);
//        String name=Double.toString(Math.random());
//
//        User testUser=User.builder()
//                .email("email")
//                .birth(birthDate)
//                .locationId("locationId")
//                .phone("phone")
//                .profileImg("profileImg")
//                .profileIntro("profileIntro")
//                .provider("provider")
//                .university("university")
//                .username(name)
//                .uuid("uuid")
//                .build();
//
//        Assertions.assertEquals(testUser.getEmail(),"email");
//        Assertions.assertEquals(testUser.getBirth(),birthDate);
//        Assertions.assertEquals(testUser.getLocationId(),"locationId");
//        Assertions.assertEquals(testUser.getPhone(),"phone");
//        Assertions.assertEquals(testUser.getProfileImg(),"profileImg");
//        Assertions.assertEquals(testUser.getProfileIntro(),"profileIntro");
//        Assertions.assertEquals(testUser.getProvider(),"provider");
//        Assertions.assertEquals(testUser.getUniversity(),"university");
//        Assertions.assertEquals(testUser.getUsername(),name);
//        Assertions.assertEquals(testUser.getUuid(),"uuid");
//    }
//}