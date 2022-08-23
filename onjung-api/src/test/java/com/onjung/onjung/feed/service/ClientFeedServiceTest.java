package com.onjung.onjung.feed.service;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.Status;
import com.onjung.onjung.feed.repository.ClientFeedRepository;
import com.onjung.onjung.user.domain.User;

import com.onjung.onjung.user.repository.UserRepository;

import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@RunWith(SpringRunner.class)
@SpringBootTest
class ClientFeedServiceTest {

    @Autowired
    private ClientFeedRepository clientFeedRepository;

    @Autowired
    private UserRepository userRepository;

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

        ClientFeed testClientFeed= ClientFeed.builder()
                .title(name)
                .body("테스트 코드입니다.")
                .writer(testUser)
                .itemId("테스트 코드입니다.")
                .build();

        clientFeedRepository.save(testClientFeed);

        Optional<ClientFeed> savedFeed= clientFeedRepository.findByTitle(name);
        System.out.println("savedFeed.get() = " + savedFeed.get());

        System.out.println("clientFeedRepository.findAll() = " + clientFeedRepository.findAll());

        org.assertj.core.api.Assertions
                .assertThat(testClientFeed).isSameAs(savedFeed.get());
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

        ClientFeed testClientFeed= ClientFeed.builder()
                .title(name)
                .body("테스트 코드입니다.")
                .writer(testUser)
                .itemId("테스트 코드입니다.")
                .build();

        clientFeedRepository.save(testClientFeed);

        System.out.println("clientFeedRepository.findById(1L).get().getStatus() = " + clientFeedRepository.findById(1L).get().getStatus());
        
        org.assertj.core.api.Assertions
                .assertThat(clientFeedRepository.findAll().get(0).getStatus())
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

        ClientFeed testClientFeed= ClientFeed.builder()
                .title(name)
                .body("테스트 코드입니다.")
                .writer(testUser)
                .itemId("테스트 코드입니다.")
                .build();

        clientFeedRepository.save(testClientFeed);

        Optional<ClientFeed> feed=clientFeedRepository.findById(1L);

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

        ClientFeed testClientFeed= ClientFeed.builder()
                .title(name)
                .body("테스트 코드입니다.")
                .writer(testUser)
                .itemId("테스트 코드입니다.")
                .build();

        clientFeedRepository.save(testClientFeed);

        final Optional<ClientFeed> clientFeed= clientFeedRepository.findById(1L);
        if(clientFeed.isPresent()){
            clientFeed.get().setTitle("수정완료했습니다.");
            clientFeed.get().setBody("수정완료했습니다.");
            clientFeed.get().setItemId("수정완료했습니다.");
        }
        clientFeedRepository.save(clientFeed.get());

        final Optional<ClientFeed> patchedClientFeed= clientFeedRepository.findById(1L);
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

        ClientFeed testClientFeed= ClientFeed.builder()
                .title(name)
                .body("테스트 코드입니다.")
                .writer(testUser)
                .itemId("테스트 코드입니다.")
                .build();

        clientFeedRepository.save(testClientFeed);

        int repoSize=clientFeedRepository.findAll().size();

        Optional<ClientFeed> clientFeed=clientFeedRepository.findById(1L);
        if(clientFeed.isPresent()){
            clientFeedRepository.delete(clientFeed.get());
        }

        org.assertj.core.api.Assertions
                .assertThat(clientFeedRepository.findAll().size())
                .isEqualTo(repoSize-1);
    }
}