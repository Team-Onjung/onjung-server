package com.onjung.onjung.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UserRequestDto {

    String email;

    //비밀번호, OAUTH 구현이후 삭제할지는 고민 필요
    String password;

    String uuid;

    String location_id;

    String provider;

    String profileImg;

    String profileIntro;

    String phone;

    String username;

    LocalDate birth;

    String university;

    @Builder
    public UserRequestDto(String email,
                          String password,
                          String uuid,
                          String location_id,
                          String provider,
                          String profileImg,
                          String profileIntro,
                          String phone,
                          String username,
                          LocalDate birth,
                          String university) {
        this.email = email;
        this.password=password;
        this.uuid = uuid;
        this.location_id = location_id;
        this.provider = provider;
        this.profileImg = profileImg;
        this.profileIntro = profileIntro;
        this.phone = phone;
        this.username = username;
        this.birth = birth;
        this.university = university;
    }
}
