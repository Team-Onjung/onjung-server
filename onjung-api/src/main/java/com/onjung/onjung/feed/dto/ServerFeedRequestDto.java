package com.onjung.onjung.feed.dto;

import com.onjung.onjung.feed.domain.Category;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.user.domain.User;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ServerFeedRequestDto extends FeedRequestDto{
    private Long minimumDuration;
    private int rentalFee;
    private int deposit;

    public ServerFeed toEntity(User user, Category category) {
        return ServerFeed.builder()
                .writer(user)
                .title(this.getTitle())
                .content(this.getContent())
                .category(category)
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .duration(this.getDuration())
                .image(this.getImage())
                .minimumDuration(this.getMinimumDuration())
                .rentalFee(this.getRentalFee())
                .deposit(this.getDeposit())
                .commissionFee(calcCommissionFee())
                .build();
    }

    public ServerFeed updatedEntity (ServerFeed feed, Category category) {
        feed.setTitle(this.getTitle());
        feed.setContent(this.getContent());
        feed.setCategory(category);
        feed.setStartDate(this.getStartDate());
        feed.setEndDate(this.getEndDate());
        feed.setDuration(this.getDuration());
        feed.setImage(this.getImage());
        feed.setMinimumDuration(this.getMinimumDuration());
        feed.setRentalFee(this.getRentalFee());
        feed.setDeposit(this.getDeposit());
        feed.setCommissionFee(Math.round(this.getRentalFee() * 0.05));
        return feed;
    }

    public Long calcCommissionFee() {
        return Math.round(this.getRentalFee() * 0.05);
    }
}
