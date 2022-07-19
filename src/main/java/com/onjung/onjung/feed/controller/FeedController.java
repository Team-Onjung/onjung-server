package com.onjung.onjung.feed.controller;

import com.onjung.onjung.feed.dto.FeedRequestDto;
import com.onjung.onjung.feed.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
public class FeedController {

    private final FeedService feedService;

    @PostMapping("/client")
    public void createClientFeed(@Valid @RequestBody FeedRequestDto requestDto) {
        try {
            feedService.saveClientFeed(requestDto);
        }catch (Exception e){
            e.printStackTrace();
        }

        return;
    }

    @PostMapping("/server")
    public void createServerFeed(@Valid @RequestBody FeedRequestDto requestDto) {
        try {
            feedService.saveServerFeed(requestDto);
        }catch (Exception e){
            e.printStackTrace();
        }

        return;
    }
}
