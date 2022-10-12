package com.onjung.onjung.user.domain;

import lombok.Getter;

@Getter
public enum Provider {

    KAKAO("kakao");

    Provider(String value) {
        this.value = value;
    }

    private String value;
}


