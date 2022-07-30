package com.onjung.onjung.feed.controller;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import com.onjung.onjung.feed.service.ServerFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
    public List<ServerFeed> readAllFeed(){

        return feedService.readAllFeed();
    }

    @GetMapping("/feed/{feedId}")
    public ResponseEntity readFeed(@PathVariable("feedId") Long feedId){
        try {
            Optional<ServerFeed> feed = feedService.readFeed(feedId);
            return ResponseEntity.status(HttpStatus.OK).body(feed);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exception raised in ClientFeedController/readFeed");
        }
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