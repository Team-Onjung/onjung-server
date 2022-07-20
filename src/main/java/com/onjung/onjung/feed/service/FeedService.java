package com.onjung.onjung.feed.service;

import com.onjung.onjung.feed.dto.FeedRequestDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface FeedService {

    void createFeed(FeedRequestDto feedRequestDto) throws Exception;

    List readAllFeed();

    Optional readFeed(Long feedId);

    void patchFeed(Long feedId, FeedRequestDto requestDto);

    void deleteFeed(Long feedId);
}
