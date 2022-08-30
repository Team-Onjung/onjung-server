package com.onjung.onjung.feed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.repository.ClientFeedRepository;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ClientFeedControllerTest {

    @Autowired
    private ClientFeedController clientFeedController;

    @Autowired
    public ClientFeedRepository clientFeedRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    public MockMvc mockMvc;

    @Test
    @BeforeEach
    void createFeed() throws Exception {

        Map<String, Object> data = new HashMap<>();

        User testUser=new User();

        data.put("title","title");
        data.put("body", "body");
        data.put("itemId","itemId");

        ObjectMapper objectMapper=new ObjectMapper();

        //성공 로직
        ResultActions SuccessedResultActions=mockMvc.perform(post("/client/feed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isOk());

        //실패로직
        data.remove("title");
        ResultActions FailedResultActions=mockMvc.perform(post("/client/feed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void readAllFeed() throws Exception {
        ResultActions resultActions=mockMvc.perform(get("/client/feed")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

    @Test
    void readFeed() throws Exception {
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

        ResultActions resultActions=mockMvc.perform(get("/client/feed/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void updateFeed() throws Exception {

        //초기 데이터 설정
        Map<String, Object> data = new HashMap<>();

        String title=Double.toString(Math.random());

        data.put("title",title);
        data.put("body", "body");
        data.put("itemId","itemId");

        ObjectMapper objectMapper=new ObjectMapper();

        //데이터 설정 성공 로직
        ResultActions settingResultActions=mockMvc.perform(post("/client/feed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isOk());

        Long id=clientFeedRepository.findByTitle(title).get().getId();

        data.replace("title","new title");
        data.replace("body", "new body");
        data.replace("itemId","new itemId");

        //데이터 수정 api 성공 로직
        ResultActions SuccessedResultActions=mockMvc.perform(patch("/client/feed/"+Long.toString(id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isOk());

        //데이터 수정 검증 로직
        MvcResult findresultActions=mockMvc.perform(get("/client/feed/"+Long.toString(id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseString=findresultActions.getResponse().getContentAsString();
        System.out.println("responseString.isBlank() = " + responseString.isBlank());

        Assertions.assertEquals("new title",clientFeedRepository.findById(id).get().getTitle());
        Assertions.assertEquals("new body",clientFeedRepository.findById(id).get().getBody());
        Assertions.assertEquals("new itemId",clientFeedRepository.findById(id).get().getItemId());

        //데이터 수정 api 실패 로직
        ResultActions FailedResultActions=mockMvc.perform(patch("/client/feed/feedID")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @AfterEach
    void deleteFeed() throws Exception {
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

        ResultActions SuccessedResultActions=mockMvc.perform(delete("/client/feed/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void lendFeed() throws Exception {
        Map<String, Object> data = new HashMap<>();

        User testUser=new User();

        data.put("title","title");
        data.put("body", "body");
        data.put("itemId","itemId");

        ObjectMapper objectMapper=new ObjectMapper();

        //성공 로직
        ResultActions SuccessedResultActions=mockMvc.perform(post("/client/feed/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isOk());
    }
}
