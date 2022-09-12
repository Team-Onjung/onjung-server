package com.onjung.onjung.review.service;

import com.onjung.onjung.review.domain.Review;
import com.onjung.onjung.review.dto.ReviewRequestDto;
import com.onjung.onjung.review.repository.ReviewRepository;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    private final UserRepository userRepository;

    @Transactional
    public void createReview(ReviewRequestDto reviewRequestDto, User sender){

        User receiveUser = reviewRequestDto.getReceiver();

        receiveUser.updateRate(reviewRequestDto.getRate());

        Review review = Review.builder()
                .receiver(reviewRequestDto.getReceiver())
                .content(reviewRequestDto.getContent())
                .rate(reviewRequestDto.getRate())
                .sender(sender)
                .build();
    }


}
