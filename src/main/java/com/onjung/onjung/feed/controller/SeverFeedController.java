package com.onjung.onjung.feed.controller;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import com.onjung.onjung.feed.service.ServerFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/server")
public class SeverFeedController implements FeedController{

    private final ServerFeedService feedService;

    @PostMapping("/feed")
    public ResponseEntity createFeed(@Valid @RequestBody FeedRequestDto requestDto) {
        try {
            feedService.createFeed(requestDto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exception raised in SeverFeedController/createFeed");
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @GetMapping("/feed")
    public Flux<ServerFeed> readAllFeed(){

        return feedService.readAllFeed();
    }

    @GetMapping("/feed/{feedId}")
    public Mono<ServerFeed> readFeed(@PathVariable("feedId") Long feedId){
            Mono<ServerFeed> feed = feedService.readFeed(feedId);
            return feed;
    }

    @PatchMapping("/feed/{feedId}")
    public ResponseEntity updateFeed(@PathVariable("feedId") Long feedId, @Valid @RequestBody FeedRequestDto requestDto) {
        try {
            feedService.patchFeed(feedId, requestDto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exception raised in SeverFeedController/updateFeed");
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @DeleteMapping("/feed/{feedId}")
    public void deleteFeed (@PathVariable("feedId") Long feedId){
        feedService.deleteFeed(feedId);
    }
}
