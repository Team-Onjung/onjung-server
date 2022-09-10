package com.onjung.onjung.user.dto;

import com.onjung.onjung.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MypageResponseDto {

    String uuid;

    String location_id;

    String provider;

    String profileImg;

    String profileIntro;

    String phone;

    String username;

    LocalDate birth;

    String university;

}
