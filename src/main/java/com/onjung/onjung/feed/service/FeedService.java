package com.onjung.onjung.feed.service;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FeedService {

    void createFeed(FeedRequestDto feedRequestDto) throws Exception;

    Flux readAllFeed();

    Mono readFeed(Long feedId) throws InterruptedException;

    void patchFeed(Long feedId, FeedRequestDto requestDto);

    void deleteFeed(Long feedId);
}
