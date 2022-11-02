package com.onjung.onjung.review.controller;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final UserRepository userRepository;

    private final ReviewRepository reviewRepository;

    private final ReviewService reviewService;

    @PostMapping("/review")
    public ResponseEntity createReview(@RequestBody @Valid ReviewRequestDto requestDto, BindingResult result) throws Exception{

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if ((String)principal!= "anonymousUser") {
            Optional<User> _user = userRepository.findByUsername((String) principal);

            if (_user.isPresent()) {
                User user = _user.get();
                reviewService.createReview(requestDto, user);

                if (result.hasErrors()) {
                    throw new InvalidParameterException(result);
                }

                return ResponseEntity.status(HttpStatus.OK).body("ok");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user not found");
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("you need to login");
        }
    }

    @DeleteMapping("/review/{reviewId}")
    public ResponseEntity deleteReview(@PathVariable("reviewId") Long reviewId) throws DataNotFoundException{
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if ((String)principal!= "anonymousUser"){

            Optional<User> user = userRepository.findByEmail((String) principal);
            Optional<Review> findReview = reviewRepository.findById(reviewId);

            if (user.isPresent() && user.get().equals(findReview.get().getSender())){
                if (user.get().equals(findReview.get().getSender()))
                    reviewService.deleteReview(reviewId);
                return ResponseEntity.status(HttpStatus.OK).body("ok");
            }else
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("you can not delete feed that you did not write");
        }
        else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("you can not delete feed that you did not write");

    }

}
