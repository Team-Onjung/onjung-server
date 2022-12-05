package com.onjung.onjung.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onjung.onjung.common.auth.application.TokenProvider;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.dto.AdditionalInfoRequestDTO;
import com.onjung.onjung.user.dto.UserRequestDto;
import com.onjung.onjung.user.service.UserSecurityService;
import com.onjung.onjung.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value =  "/user", produces = "application/json; charset=utf8")
public class UserController {
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Valid @RequestBody UserRequestDto requestDto) {
        try {
            User user = userService.saveUser(requestDto);
            ObjectMapper mapper = new ObjectMapper();
            String jwtToken = tokenProvider.createToken(user);
            Map<String, String> json = new HashMap<>();
            json.put("msg", "정상적으로 토큰이 발급되었습니다");
            json.put("token", jwtToken);
            String jsonResponse = mapper.writeValueAsString(ResponseEntity.status(200).body(json));
            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/etc")
    public void collectAdditionalInfo(@RequestBody AdditionalInfoRequestDTO additionalInfoRequestDTO){
        userService.collectAdditionalInfo(additionalInfoRequestDTO);
    }

    @GetMapping
    public List<User> readAllUser(){
        return  userService.findAllUsers();
    }
}
