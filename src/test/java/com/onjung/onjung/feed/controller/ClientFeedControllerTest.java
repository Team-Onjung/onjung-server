package com.onjung.onjung.feed.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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

//        Map<String, Object> data = new HashMap<>();
//
//        data.put("email","email");
//        data.put("uuid", "uuid");
//        data.put("location_id","location_id");
//        data.put("provider","provider");
//        data.put("profileImg","profileImg");
//        data.put("profileIntro","profileIntro");
//        data.put("phone","phone");
//        data.put("username","username");
//        data.put("birth","2022-07-18");
//        data.put("university","university");
//
//        ObjectMapper objectMapper=new ObjectMapper();
////        System.out.println("this.mockMvc = " + mockMvc);
//
//        ResultActions resultActions=mockMvc.perform(post("/client/feed")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(data)))
//                .andExpect(status().isOk());
    }

//    @Test
//    void readAllFeed() throws Exception {
//        ResultActions resultActions=mockMvc.perform(get("/client/feed")
//                        .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isOk());
//    }

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
