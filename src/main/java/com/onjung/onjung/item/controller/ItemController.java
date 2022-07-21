package com.onjung.onjung.item.controller;


import com.onjung.onjung.item.dto.ItemDto;
import com.onjung.onjung.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/enroll")
    public void enroll(@Valid @RequestBody ItemDto requestDto){
        try{
            itemService.saveItem(requestDto);
        }catch(Exception e){
            e.printStackTrace();
        }
        return;
    }
}
