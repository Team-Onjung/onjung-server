package com.onjung.onjung.review.service;

import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.exception.UnauthorizedException;
import com.onjung.onjung.review.domain.Review;
import com.onjung.onjung.review.dto.ReviewRequestDto;
import com.onjung.onjung.review.repository.ReviewRepository;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


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

    @Transactional
    public void deleteReview(Long reviewId){

        Optional<Review> review = reviewRepository.findById(reviewId);

        if (review.isPresent()){
                reviewRepository.delete(review.get());
        }else
            throw new DataNotFoundException();
    }

}
