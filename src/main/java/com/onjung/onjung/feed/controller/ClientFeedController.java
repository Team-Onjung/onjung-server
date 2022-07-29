package com.onjung.onjung.feed.controller;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import com.onjung.onjung.feed.service.ClientFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientFeedController implements FeedController{

    private final ClientFeedService feedService;

    @PostMapping("/feed")
    public ResponseEntity createFeed(@Valid @RequestBody FeedRequestDto requestDto) {
        try {
            feedService.createFeed(requestDto);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exception raised in ClientFeedController/createFeed");
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @GetMapping("/feed")
//    @Async
    public Flux<ClientFeed> readAllFeed(){
        return feedService.readAllFeed();
    }

    @GetMapping("/feed/{feedId}")
//    @Async
    public Mono<ClientFeed> readFeed(@PathVariable("feedId") Long feedId) throws InterruptedException {
            Mono<ClientFeed> feed = feedService.readFeed(feedId);
            return feed;
    }

    @PatchMapping("/feed/{feedId}")
    public ResponseEntity updateFeed(@PathVariable("feedId") Long feedId, @Valid @RequestBody FeedRequestDto requestDto) {
        try {
            feedService.patchFeed(feedId, requestDto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exception raised in ClientFeedController/updateFeed");
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @DeleteMapping("/feed/{feedId}")
    public void deleteFeed (@PathVariable("feedId") Long feedId){
        feedService.deleteFeed(feedId);
    }
}
