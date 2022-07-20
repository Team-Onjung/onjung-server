package com.onjung.onjung.feed.service;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.Status;
import com.onjung.onjung.feed.repository.ClientFeedRepository;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;

@DataJpaTest
class ClientFeedServiceTest {

    private ClientFeedRepository clientFeedRepository;

    private UserRepository userRepository;

    @Test
    void createFeed() {

        User testUser=User.builder()
                .email("테스트 코드입니다.")
                .birth(LocalDate.MAX)
                .location_id("테스트 코드입니다.")
                .phone("테스트 코드입니다.")
                .profileImg("테스트 코드입니다.")
                .profileIntro("테스트 코드입니다.")
                .provider("테스트 코드입니다.")
                .university("테스트 코드입니다.")
                .username("테스트 코드입니다.")
                .uuid("테스트 코드입니다.")
                .build();

//        userRepository.save(testUser);

        ClientFeed testclientFeed= ClientFeed.builder()
                .title("테스트 코드입니다.")
                .body("테스트 코드입니다.")
                .writer(testUser)
                .itemId("테스트 코드입니다.")
                .status(Status.STATUS_POSSIBLE)
                .build();

//        clientFeedRepository.save(testclientFeed);
//
//        Optional<ClientFeed> savedFeed= clientFeedRepository.findByTitle("테스트 코드입니다.");
//        Assertions.assertEquals(savedFeed.get().getBody(),"테스트 코드입니다.");
    }


//    @Test
//    void readAllFeed() {
//    }
//
//    @Test
//    void readFeed() {
//    }
//
//    @Test
//    void patchFeed() {
//    }
//
//    @Test
//    void deleteFeed() {
//    }
}