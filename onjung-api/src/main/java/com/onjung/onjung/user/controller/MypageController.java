package com.onjung.onjung.user.controller;

import com.onjung.onjung.user.dto.MypageResponseDto;
import com.onjung.onjung.user.service.MypageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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