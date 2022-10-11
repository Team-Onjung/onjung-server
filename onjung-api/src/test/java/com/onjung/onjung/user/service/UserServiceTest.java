//package com.onjung.onjung.user.service;
//
//import com.onjung.onjung.user.domain.User;
//import com.onjung.onjung.user.repository.UserRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
//
//import java.time.LocalDate;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//class UserServiceTest {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Test
//    void saveUser() {
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
//
//        userRepository.save(testUser);
//
//        Optional<User> savedUser= userRepository.findByUsername(name);
//
//        if(savedUser.isPresent()){
//            Assertions.assertEquals(savedUser.get().getIsActive(),true);
//            Assertions.assertEquals(savedUser.get().getIsBlocked(),false);
//            Assertions.assertEquals(savedUser.get().getIsUniversity(),false);
//        }
//    }
//}