package com.onjung.onjung.feed.dto;

import com.onjung.onjung.feed.domain.Category;
import com.onjung.onjung.user.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class ServerFeedRequestDto {

    Long categoryId;

    User writer;

    String title;

    LocalDateTime startDate;

    LocalDateTime endDate;

    LocalDateTime duration;

    String content;

    String image;

    int rentalFee;

    int deposit;



    @Builder
    public ServerFeedRequestDto(
            Long categoryId,
            User writer,
            String title,
            String content,
            LocalDateTime startDate,
            LocalDateTime endDate,
            LocalDateTime duration,
            String image,
            int rentalFee,
            int deposit
    )
    {
        this.writer = writer;
        this.categoryId = categoryId;
        this.content = content;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.image = image;
        this.rentalFee = rentalFee;
        this.deposit = deposit;

    }

}
