package com.onjung.onjung.feed.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.Status;
import com.onjung.onjung.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientFeedControllerTest {

    @Autowired
    private ClientFeedController clientFeedController;

    @Autowired
    private MockMvc mockMvc;

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

//    @Test
//    void readFeed() {
//    }
//
//    @Test
//    void updateFeed() {
//    }
//
//    @Test
//    void deleteFeed() {
//    }
}
