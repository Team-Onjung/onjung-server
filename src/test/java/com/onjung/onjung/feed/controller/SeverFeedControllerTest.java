package com.onjung.onjung.feed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onjung.onjung.feed.repository.ServerFeedRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SeverFeedControllerTest {


    @Autowired
    private SeverFeedController severFeedController;

    @Autowired
    private ServerFeedRepository serverFeedRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createFeed() throws Exception {

        Map<String, Object> data = new HashMap<>();

        data.put("title","title");
        data.put("body", "body");
        data.put("itemId","itemId");

        ObjectMapper objectMapper=new ObjectMapper();

        //성공 로직
        ResultActions SuccessedResultActions=mockMvc.perform(post("/server/feed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isOk());

        //실패로직
        data.remove("title");
        ResultActions FailedResultActions=mockMvc.perform(post("/server/feed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void readAllFeed() throws Exception {
        ResultActions resultActions=mockMvc.perform(get("/server/feed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void readFeed() throws Exception {

        ResultActions resultActions=mockMvc.perform(get("/server/feed/1")
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
        ResultActions settingResultActions=mockMvc.perform(post("/server/feed")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isOk());

        Long id=serverFeedRepository.findByTitle(title).get().getId();

        data.replace("title","new title");
        data.replace("body", "new body");
        data.replace("itemId","new itemId");


        //데이터 수정 api 성공 로직
        ResultActions SuccessedResultActions=mockMvc.perform(patch("/server/feed/"+Long.toString(id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isOk());

        //데이터 수정 검증 로직
        MvcResult findresultActions=mockMvc.perform(get("/server/feed/"+Long.toString(id))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseString=findresultActions.getResponse().getContentAsString();
//        System.out.println("responseString = " + responseString);
        Assertions.assertEquals(responseString.contains("new title"),true);
        Assertions.assertEquals(responseString.contains("new body"),true);
        Assertions.assertEquals(responseString.contains("new itemId"),true);

        //데이터 수정 api 실패 로직
        ResultActions FailedResultActions=mockMvc.perform(patch("/server/feed/feedID")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteFeed() throws Exception {
        ObjectMapper objectMapper=new ObjectMapper();

        ResultActions SuccessedResultActions=mockMvc.perform(delete("/server/feed/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}