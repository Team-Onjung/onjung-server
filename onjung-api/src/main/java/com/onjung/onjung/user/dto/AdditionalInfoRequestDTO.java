package com.onjung.onjung.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdditionalInfoRequestDTO {

    //    사용자의 풀네임(예:김멋사)
    private String fullName;

    //    대학교이름
    private String universityName;

    //    프로필 이미지
    private String profileImg;

    //    전화번호
    private String phone;
}
