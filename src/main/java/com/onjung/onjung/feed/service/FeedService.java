package com.onjung.onjung.feed.service;

import com.onjung.onjung.feed.domain.Feed;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import com.onjung.onjung.user.domain.User;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface FeedService {

    void createFeed(FeedRequestDto feedRequestDto, User user) throws Exception;

    List readAllFeed();

    Optional readFeed(Long feedId) throws InterruptedException;

    Feed patchFeed(Long feedId, FeedRequestDto requestDto);

    void deleteFeed(Long feedId);
}