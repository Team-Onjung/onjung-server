package com.onjung.onjung.feed.controller;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.WebAsyncTask;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface FeedController {

    ResponseEntity createFeed(FeedRequestDto requestDto);

    List readAllFeed() throws ExecutionException, InterruptedException, TimeoutException;

    ResponseEntity readFeed(Long feedId) throws ExecutionException, TimeoutException;

    ResponseEntity updateFeed(Long feedId, FeedRequestDto requestDto);

    void deleteFeed (Long feedId);
}