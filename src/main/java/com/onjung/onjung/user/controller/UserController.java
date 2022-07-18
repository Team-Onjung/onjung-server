package com.onjung.onjung.user.controller;

import com.onjung.onjung.user.dto.UserRequestDto;
import com.onjung.onjung.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@Valid @RequestBody UserRequestDto requestDto) {

        try {
            userService.saveUser(requestDto);
        }catch (Exception e){
            e.printStackTrace();
        }

        return;
    }
}
