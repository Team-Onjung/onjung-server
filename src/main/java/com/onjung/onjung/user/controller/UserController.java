//package com.onjung.onjung.user.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/user")
//public class UserController {
//
//    @PostMapping
//    public BasePointResponseDto update(@RequestBody BasePointRequestDto requestDto) {
//
//        System.out.println("requestDto = " + requestDto.getAddress());
//        BasePointResponseDto responseDto;
//        responseDto = basePointService.saveAddress(requestDto);
//        System.out.println(responseDto.getAddressId());
//        return responseDto;
//    }
//}
