package com.onjung.onjung.user.controller;

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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserSecurityService userSecurityService;

    private final UserService userService;

    @PostMapping("/signup")
    public void signup(@Valid @RequestBody UserRequestDto requestDto) {

        try {
            userService.saveUser(requestDto);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody UserRequestDto requestDto) throws Exception {

        final UserDetails userDetails = userSecurityService
                .loadUserByUsername(requestDto.getUsername());

        authenticate(requestDto.getUsername(), requestDto.getPassword());

        return ResponseEntity.status(HttpStatus.OK).body(userDetails);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e){
            e.printStackTrace();
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
