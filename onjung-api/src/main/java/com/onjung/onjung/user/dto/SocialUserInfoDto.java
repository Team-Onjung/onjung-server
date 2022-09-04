package com.onjung.onjung.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Getter
@NoArgsConstructor
public class SocialUserInfoDto {
    private Long id;
    private String username;
    private String email;

    @Enumerated(EnumType.STRING)
    private String provider;

    public SocialUserInfoDto(Long id, String username, String email, String provider) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.provider = provider;
    }


}