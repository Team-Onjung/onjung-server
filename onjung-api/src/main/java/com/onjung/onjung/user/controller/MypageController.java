package com.onjung.onjung.user.controller;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.repository.ClientFeedRepository;
import com.onjung.onjung.feed.repository.ServerFeedRepository;
import com.onjung.onjung.user.dto.MypageResponseDto;
import com.onjung.onjung.user.repository.UserRepository;
import com.onjung.onjung.user.service.MypageService;
import com.onjung.onjung.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class MypageController {

    private final MypageService mypageService;

    @GetMapping("/mypage")
    public MypageResponseDto mypage(){
        return mypageService.mypageData();
    }
}
