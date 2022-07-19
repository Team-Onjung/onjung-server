package com.onjung.onjung.feed.controller;

import com.onjung.onjung.feed.dto.FeedRequestDto;

import java.util.List;
import java.util.Optional;

public interface FeedController {

    void createFeed(FeedRequestDto requestDto);

    List readAllFeed();

    Optional readFeed(Long feedId);

    void updateFeed(Long feedId, FeedRequestDto requestDto);

    void deleteFeed (Long feedId);
}
