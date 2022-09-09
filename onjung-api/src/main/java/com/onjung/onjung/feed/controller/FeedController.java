package com.onjung.onjung.feed.controller;

import com.onjung.onjung.feed.dto.FeedRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface FeedController {

    ResponseEntity createFeed(FeedRequestDto requestDto, BindingResult result) throws Exception;

    List readAllFeed() throws ExecutionException, InterruptedException, TimeoutException;

    ResponseEntity readFeed(Long feedId) throws ExecutionException, TimeoutException, InterruptedException;

    ResponseEntity updateFeed(Long feedId, FeedRequestDto requestDto, BindingResult result);

    ResponseEntity deleteFeed (Long feedId);

    ResponseEntity searchFeed (String query);
}