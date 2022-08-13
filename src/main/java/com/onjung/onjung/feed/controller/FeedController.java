package com.onjung.onjung.feed.controller;

import com.onjung.onjung.feed.dto.FeedRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface FeedController {

    ResponseEntity createFeed(FeedRequestDto requestDto) throws Exception;

    WebAsyncTask readAllFeed();

    ResponseEntity readFeed(Long feedId);

    ResponseEntity updateFeed(Long feedId, FeedRequestDto requestDto);

    void deleteFeed (Long feedId);
}