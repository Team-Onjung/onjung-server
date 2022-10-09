package com.onjung.onjung.feed.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.onjung.onjung.feed.domain.Category;
import com.onjung.onjung.user.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Getter @Setter
public class ClientFeedRequestDto extends FeedRequestDto{
    String content;
    String image;
    Long pricePerDay;
}
