package com.onjung.onjung.feed.service;

import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.domain.Status;
import com.onjung.onjung.feed.repository.ServerFeedRepository;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@SpringBootTest
class ServerFeedServiceTest {
    @Autowired
    private ServerFeedRepository serverFeedRepository;

    @Autowired
    private UserRepository userRepository;

    Logger logger= LoggerFactory.getLogger(ClientFeedServiceTest.class);

    @Test
    @Transactional
    void createFeed() {

        LocalDate birthDate= LocalDate.ofYearDay(2022,1);
        String name=Double.toString(Math.random());

        User testUser=User.builder()
                .email("테스트")
                .birth(birthDate)
                .locationId("테스트")
                .phone("테스트")
                .profileImg("테스트")
                .profileIntro("테스트")
                .provider("테스트")
                .university("테스트")
                .username(name)
                .uuid("테스트")
                .build();

        userRepository.save(testUser);

        ServerFeed testserverFeed= ServerFeed.builder()
                .title(name)
                .body("테스트 코드입니다.")
                .writer(testUser)
                .itemId("테스트 코드입니다.")
                .build();

        serverFeedRepository.save(testserverFeed);

        Optional<ServerFeed> savedFeed= serverFeedRepository.findByTitle(name);

        org.assertj.core.api.Assertions
                .assertThat(testserverFeed).isSameAs(savedFeed.get());
        org.assertj.core.api.Assertions
                .assertThat(savedFeed.get().getId()).isNotNull();
    }


    @Test
    void readAllFeed() {
        LocalDate birthDate= LocalDate.ofYearDay(2022,1);
        String name=Double.toString(Math.random());

        User testUser=User.builder()
                .email("테스트")
                .birth(birthDate)
                .locationId("테스트")
                .phone("테스트")
                .profileImg("테스트")
                .profileIntro("테스트")
                .provider("테스트")
                .university("테스트")
                .username(name)
                .uuid("테스트")
                .build();

        userRepository.save(testUser);

        ServerFeed testserverFeed= ServerFeed.builder()
                .title(name)
                .body("테스트 코드입니다.")
                .writer(testUser)
                .itemId("테스트 코드입니다.")
                .build();

        serverFeedRepository.save(testserverFeed);

        org.assertj.core.api.Assertions
                .assertThat(serverFeedRepository.findAll().get(0).getStatus())
                .isEqualTo(Status.STATUS_POSSIBLE);
    }

    @Test
    void readFeed() {
        LocalDate birthDate= LocalDate.ofYearDay(2022,1);
        String name=Double.toString(Math.random());

        User testUser=User.builder()
                .email("테스트")
                .birth(birthDate)
                .locationId("테스트")
                .phone("테스트")
                .profileImg("테스트")
                .profileIntro("테스트")
                .provider("테스트")
                .university("테스트")
                .username(name)
                .uuid("테스트")
                .build();

        userRepository.save(testUser);

        ServerFeed testserverFeed= ServerFeed.builder()
                .title(name)
                .body("테스트 코드입니다.")
                .writer(testUser)
                .itemId("테스트 코드입니다.")
                .build();

        serverFeedRepository.save(testserverFeed);
        Optional<ServerFeed> feed=serverFeedRepository.findById(1L);

        org.assertj.core.api.Assertions
                .assertThat(feed.get().getStatus())
                .isEqualTo(Status.STATUS_POSSIBLE);
    }

    @Test
    void patchFeed() {
        LocalDate birthDate= LocalDate.ofYearDay(2022,1);
        String name=Double.toString(Math.random());

        User testUser=User.builder()
                .email("테스트")
                .birth(birthDate)
                .locationId("테스트")
                .phone("테스트")
                .profileImg("테스트")
                .profileIntro("테스트")
                .provider("테스트")
                .university("테스트")
                .username(name)
                .uuid("테스트")
                .build();

        userRepository.save(testUser);

        ServerFeed testserverFeed= ServerFeed.builder()
                .title(name)
                .body("테스트 코드입니다.")
                .writer(testUser)
                .itemId("테스트 코드입니다.")
                .build();

        serverFeedRepository.save(testserverFeed);

        Optional<ServerFeed> clientFeed= serverFeedRepository.findById(1L);
        if(clientFeed.isPresent()){
            clientFeed.get().setTitle("수정완료했습니다.");
            clientFeed.get().setBody("수정완료했습니다.");
            clientFeed.get().setItemId("수정완료했습니다.");
        }
        serverFeedRepository.save(clientFeed.get());

        final Optional<ServerFeed> patchedClientFeed= serverFeedRepository.findById(1L);
        org.assertj.core.api.Assertions
                .assertThat(patchedClientFeed.get().getTitle())
                .isEqualTo("수정완료했습니다.");
    }

    @Test
    void deleteFeed() {
        LocalDate birthDate= LocalDate.ofYearDay(2022,1);
        String name=Double.toString(Math.random());

        User testUser=User.builder()
                .email("테스트")
                .birth(birthDate)
                .locationId("테스트")
                .phone("테스트")
                .profileImg("테스트")
                .profileIntro("테스트")
                .provider("테스트")
                .university("테스트")
                .username(name)
                .uuid("테스트")
                .build();

        userRepository.save(testUser);

        ServerFeed testserverFeed= ServerFeed.builder()
                .title(name)
                .body("테스트 코드입니다.")
                .writer(testUser)
                .itemId("테스트 코드입니다.")
                .build();

        serverFeedRepository.save(testserverFeed);

        int repoSize=serverFeedRepository.findAll().size();

        Optional<ServerFeed> clientFeed=serverFeedRepository.findById(1L);
        if(clientFeed.isPresent()){
            serverFeedRepository.delete(clientFeed.get());
        }

        org.assertj.core.api.Assertions
                .assertThat(serverFeedRepository.findAll().size())
                .isEqualTo(repoSize-1);
    }
}