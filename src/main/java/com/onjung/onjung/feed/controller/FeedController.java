package com.onjung.onjung.feed.controller;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FeedController {

    ResponseEntity createFeed(FeedRequestDto requestDto);

    Flux readAllFeed();

    Mono readFeed(Long feedId) throws InterruptedException;

    ResponseEntity updateFeed(Long feedId, FeedRequestDto requestDto);

    void deleteFeed (Long feedId);
}
