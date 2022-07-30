package com.onjung.onjung.feed.controller;

import com.onjung.onjung.feed.dto.FeedRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface FeedController {

    ResponseEntity createFeed(FeedRequestDto requestDto);

    List readAllFeed();

    ResponseEntity readFeed(Long feedId);

    ResponseEntity updateFeed(Long feedId, FeedRequestDto requestDto);

    void deleteFeed (Long feedId);
}