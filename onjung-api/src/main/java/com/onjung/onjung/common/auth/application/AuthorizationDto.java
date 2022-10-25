package com.onjung.onjung.common.auth.application;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.*;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
@Getter
public class AuthorizationDto {
    String username;
    String token;

    public AuthorizationDto(String username, String token) {
        this.username = username;
        this.token = "Bearer "+ token;
    }
}