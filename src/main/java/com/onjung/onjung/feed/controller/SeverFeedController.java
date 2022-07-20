package com.onjung.onjung.feed.controller;

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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("requested data is wrong");
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @GetMapping("/feed")
    public List<ServerFeed> readAllFeed(){

        return feedService.readAllFeed();
    }

    @GetMapping("/feed/{feedId}")
    public Optional<ServerFeed> readFeed(@PathVariable("{feedId}") Long feedId){

        return feedService.readFeed(feedId);
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
