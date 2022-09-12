package com.onjung.onjung.feed.dto;

import com.onjung.onjung.feed.domain.Category;
import com.onjung.onjung.user.domain.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ClientFeedRequestDto extends FeedRequestDto{

    Long categoryId;
    User writer;
    String title;
    LocalDateTime startDate;
    LocalDateTime endDate;
    LocalDateTime duration;
    String content;
    String image;

    @Builder
    public ClientFeedRequestDto(
            Long categoryId,
            User writer,
            String title,
            LocalDateTime startDate,
            LocalDateTime endDate,
            LocalDateTime duration,
            String content,
            String image
    )
    {
        this.writer = writer;
        this.title = title;
        this.categoryId = categoryId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.content = content;
        this.image = image;
    }


}
