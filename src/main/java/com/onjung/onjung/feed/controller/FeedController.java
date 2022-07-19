package com.onjung.onjung.feed.controller;

import com.onjung.onjung.feed.dto.FeedRequestDto;

import java.util.List;
import java.util.Optional;

public interface FeedController {

    List readAllFeed();

    Optional readFeed(Long feedId);

    void createFeed(FeedRequestDto requestDto);

    void updateFeed(Long feedId, FeedRequestDto requestDto);

    void deleteFeed (Long feedId);
}
