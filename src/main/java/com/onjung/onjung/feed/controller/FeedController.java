package com.onjung.onjung.feed.controller;

import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;

public interface FeedController {

    ResponseEntity createFeed(FeedRequestDto requestDto);

    Flux readAllFeed();

    ResponseEntity readFeed(Long feedId);

    ResponseEntity updateFeed(Long feedId, FeedRequestDto requestDto);

    void deleteFeed (Long feedId);
}
