package com.onjung.onjung.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class UserRequestDto {

    @NotBlank
    String email;

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
