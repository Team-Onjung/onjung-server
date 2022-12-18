package com.onjung.onjung.common;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 서버 측에서 CORS 설정을 테스트하기 위한 임시 파일.
 * 개발 과정이 완료되면 삭제한다.
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello() {
        return "hi";
    }
}