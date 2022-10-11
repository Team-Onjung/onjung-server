package com.onjung.onjung.feed.dto;

import com.onjung.onjung.feed.domain.Category;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClientFeedRequestDto extends FeedRequestDto{
    Long pricePerDay;

    public ClientFeed toEntity(User user, Category category) {
        return ClientFeed.builder()
                .writer(user)
                .title(this.getTitle())
                .content(this.getContent())
                .category(category)
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .duration(this.getDuration())
                .image(this.getImage())
                .pricePerDay(this.getPricePerDay())
                .build();
    }

    public ClientFeed updatedEntity (ClientFeed feed, Category category) {
        feed.setTitle(this.getTitle());
        feed.setContent(this.getContent());
        feed.setCategory(category);
        feed.setStartDate(this.getStartDate());
        feed.setEndDate(this.getEndDate());
        feed.setDuration(this.getDuration());
        feed.setImage(this.getImage());
        feed.setPricePerDay(this.getPricePerDay());
        return feed;
    }
}
