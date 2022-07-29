package com.onjung.onjung.feed.controller;

import com.onjung.onjung.feed.dto.FeedRequestDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FeedController {

    ResponseEntity createFeed(FeedRequestDto requestDto);

    Flux readAllFeed();

    Mono readFeed(Long feedId) throws InterruptedException;

    ResponseEntity updateFeed(Long feedId, FeedRequestDto requestDto);

    void deleteFeed (Long feedId);
}
