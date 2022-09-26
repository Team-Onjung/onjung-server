package com.onjung.onjung.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RoomDto {

    User starter;

    // client 인지 server 인지 저장
    String feedType;
    Long feedId;

    @Builder
    public RoomDto(
            User starter,
            String feedType,
            Long feedId
    ){
        this.starter = starter;
        this.feedType = feedType;
        this.feedId = feedId;
    }

}
