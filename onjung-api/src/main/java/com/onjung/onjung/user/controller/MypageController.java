package com.onjung.onjung.user.controller;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.repository.ClientFeedRepository;
import com.onjung.onjung.feed.repository.ServerFeedRepository;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MypageController {

    private final ClientFeedRepository clientFeedRepository;
    private final ServerFeedRepository serverFeedRepository;
    private final UserRepository userRepository;

    @GetMapping("/mypage")
    public void mypage(){

    }
}
