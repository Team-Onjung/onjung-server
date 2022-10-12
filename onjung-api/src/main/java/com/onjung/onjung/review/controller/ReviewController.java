package com.onjung.onjung.review.controller;

import com.onjung.onjung.common.auth.PrincipalDetails;
import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.exception.InvalidParameterException;
import com.onjung.onjung.exception.UnauthorizedException;
import com.onjung.onjung.review.domain.Review;
import com.onjung.onjung.review.dto.ReviewRequestDto;
import com.onjung.onjung.review.repository.ReviewRepository;
import com.onjung.onjung.review.service.ReviewService;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final UserRepository userRepository;

    private final ReviewRepository reviewRepository;

    private final ReviewService reviewService;

    @PostMapping("")
    public ResponseEntity createReview(@RequestBody @Valid ReviewRequestDto requestDto, @AuthenticationPrincipal PrincipalDetails principalDetails, BindingResult result) throws Exception{

        User user = principalDetails.getUser();
        reviewService.createReview(requestDto, user);
        return ResponseEntity.status(HttpStatus.OK).body("리뷰가 등록되었습니다.");
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable("reviewId") Long reviewId,
                                       @AuthenticationPrincipal PrincipalDetails principalDetails) throws DataNotFoundException{

        User user = principalDetails.getUser();
        Review findReview = reviewRepository.findById(reviewId).orElseThrow(DataNotFoundException::new);
        if (user.equals(findReview.getSender())){
            reviewService.deleteReview(reviewId);
        }
        return ResponseEntity.status(HttpStatus.OK).body("리뷰가 삭제되었습니다.");
    }

}
