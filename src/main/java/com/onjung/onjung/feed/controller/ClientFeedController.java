package com.onjung.onjung.feed.controller;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import com.onjung.onjung.feed.service.ClientFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientFeedController implements FeedController{

    private final ClientFeedService feedService;

    @GetMapping("/feed")
    public List<ClientFeed> readAllFeed(){

        return feedService.readAllFeed();
    }

    @GetMapping("/feed/{feedId}")
    public Optional<ClientFeed> readFeed(@PathVariable("{feedId}") Long feedId){

        return feedService.readFeed(feedId);
    }

    @PostMapping("/feed")
    public void createFeed(@Valid @RequestBody FeedRequestDto requestDto) {
        try {
            feedService.createFeed(requestDto);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @PatchMapping("/feed/{feedId}")
    public void updateFeed(@PathVariable("{feedId}") Long feedId, @Valid @RequestBody FeedRequestDto requestDto) {
        try {
            feedService.patchFeed(feedId, requestDto);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @DeleteMapping("/feed/{feedId}")
    public void deleteFeed (@PathVariable("{feedId}") Long feedId){
        feedService.deleteFeed(feedId);
    }
}
