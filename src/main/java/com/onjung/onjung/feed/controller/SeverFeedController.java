package com.onjung.onjung.feed.controller;

import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import com.onjung.onjung.feed.service.ServerFeedService;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.WebAsyncTask;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/server")
public class SeverFeedController implements FeedController{

    private final ServerFeedService feedService;
    private final UserRepository userRepository;

    @PostMapping("/feed")
    public ResponseEntity createFeed(@Valid @RequestBody FeedRequestDto requestDto) throws Exception{
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        if ((String)principal!= "anonymousUser") {
            Optional<User> _user = userRepository.findByUsername((String) principal);

            if (_user.isPresent()) {
                User user = _user.get();
                System.out.println("user = " + user);
                feedService.createFeed(requestDto, user);
                return ResponseEntity.status(HttpStatus.OK).body("ok");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user not found");
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("you need to login");
        }
    }

    @GetMapping("/feed")
    public WebAsyncTask<List<ServerFeed>> readAllFeed(){

        return new WebAsyncTask<List<ServerFeed>>(feedService::readAllFeed);
    }

    @PostMapping("/feed/{feedId}")
    public ResponseEntity borrowFeed(@PathVariable("feedId") Long feedId) throws DataNotFoundException, Exception {
        feedService.borrowFeed(feedId);
        return ResponseEntity.status(HttpStatus.OK).body("borrowing is succeed");
    }

    @GetMapping("/feed/{feedId}")
    public ResponseEntity readFeed(@PathVariable("feedId") Long feedId){
            Optional<ServerFeed> feed = feedService.readFeed(feedId);
            return ResponseEntity.status(HttpStatus.OK).body(feed);

    }

    @PatchMapping("/feed/{feedId}")
    public ResponseEntity updateFeed(@PathVariable("feedId") Long feedId, @Valid @RequestBody FeedRequestDto requestDto) throws DataNotFoundException {
            feedService.patchFeed(feedId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @DeleteMapping("/feed/{feedId}")
    public void deleteFeed (@PathVariable("feedId") Long feedId){
        feedService.deleteFeed(feedId);
    }
}