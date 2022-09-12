//package com.onjung.onjung.item;
//
//import com.onjung.onjung.item.controller.ItemController;
//import com.onjung.onjung.item.repository.ItemRepository;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
//// 오류 핸들링 테스트하기 => no such element exception
//public class ItemControllerTest {
//
//    @Autowired
//    private ItemRepository itemRepository;
//
//    @Autowired
//    private ItemController itemController;
//
//    @Autowired
//    public MockMvc mockMvc;
//
//    @Test
//    public void readItem() throws Exception {
//
//        //given
//
//        ResultActions resultActions=mockMvc.perform(get("/item/1")
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound());
//
//
//        //when
//
//        //then
//
//    }
//
//}
