package com.onjung.onjung.dto;

import com.onjung.onjung.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RoomDto {

    User starter;

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
