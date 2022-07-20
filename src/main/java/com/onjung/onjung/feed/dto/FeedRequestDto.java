package com.onjung.onjung.feed.dto;

import com.onjung.onjung.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FeedRequestDto {

//    이후에 삭제 또는 변경필요
    User writer;

    String title;

    String body;

//    이후에 변경필요
    String itemId;

    @Builder
    public FeedRequestDto(User writer, String title, String body, String itemId) {
        this.writer = writer;
        this.title = title;
        this.body = body;
        this.itemId = itemId;
    }
}
