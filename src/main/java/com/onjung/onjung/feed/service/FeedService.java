package com.onjung.onjung.feed.service;

import com.onjung.onjung.feed.dto.FeedRequestDto;

import java.util.List;
import java.util.Optional;

public interface FeedService {

    List readAllFeed();

    Optional readFeed(Long feedId);

    void createFeed(FeedRequestDto feedRequestDto);

    void patchFeed(Long feedId, FeedRequestDto requestDto);

    void deleteFeed(Long feedId);
}
