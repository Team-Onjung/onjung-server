package com.onjung.onjung.review.dto;

import com.onjung.onjung.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {

    User receiver;
    User sender;
    float rate;
    String content;

}
