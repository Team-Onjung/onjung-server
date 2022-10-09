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
public class ServerFeedRequestDto extends FeedRequestDto{
    Long minimumDuration;
    int rentalFee;
    int deposit;

    @Builder
    public ServerFeedRequestDto(
            Long categoryId,
            String title,
            String content,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Long duration,
            String image,
            int rentalFee,
            int deposit
    )
    {
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
