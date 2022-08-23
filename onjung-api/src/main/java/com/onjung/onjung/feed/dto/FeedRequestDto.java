package com.onjung.onjung.feed.dto;

import com.onjung.onjung.user.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
public class FeedRequestDto {

    String title;

    String body;

//    이후에 변경필요
    Long itemId;

    @Builder
    public FeedRequestDto(String title, String body, Long itemId) {
        this.title = title;
        this.body = body;
        this.itemId = itemId;
    }
}
