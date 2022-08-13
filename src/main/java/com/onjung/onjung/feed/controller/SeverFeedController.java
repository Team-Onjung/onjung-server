package com.onjung.onjung.feed.controller;

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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/server")
public class SeverFeedController implements FeedController{

    private final ServerFeedService feedService;
    private final UserRepository userRepository;

    @PostMapping("/feed")
    public ResponseEntity createFeed(@Valid @RequestBody FeedRequestDto requestDto) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        if ((String)principal!= "anonymousUser") {
            Optional<User> _user = userRepository.findByUsername((String) principal);

            if (_user.isPresent()) {
                User user = _user.get();
                System.out.println("user = " + user);
                try {
                    feedService.createFeed(requestDto, user);
                } catch (Exception e) {
                    e.printStackTrace();
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exception raised in SeverFeedController/createFeed");
                }
                return ResponseEntity.status(HttpStatus.OK).body("ok");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("user not found");
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("you need to login");
        }
    }

    @GetMapping("/feed")
    public List<ServerFeed> readAllFeed() throws ExecutionException, InterruptedException, TimeoutException {
        return feedService.readAllFeed().get(200L, TimeUnit.MILLISECONDS);
    }

    @PostMapping("/feed/{feedId}")
    public ResponseEntity borrowFeed(@PathVariable("feedId") Long feedId) {
        try {
            feedService.borrowFeed(feedId);
            return ResponseEntity.status(HttpStatus.OK).body("borrowing is succeed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exception raised in ClientFeedController/borrowFeed");
        }
    }

    @GetMapping("/feed/{feedId}")
    public ResponseEntity readFeed(@PathVariable("feedId") Long feedId) throws ExecutionException, TimeoutException {
        try {
            ServerFeed feed = feedService.readFeed(feedId).get(200L, TimeUnit.MILLISECONDS);
            return ResponseEntity.status(HttpStatus.OK).body(feed);
        }
        catch (InterruptedException e) {
            return ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT).body(e.getMessage());
        }
    }

    @PatchMapping("/feed/{feedId}")
    public ResponseEntity updateFeed(@PathVariable("feedId") Long feedId, @Valid @RequestBody FeedRequestDto requestDto) {
        try {
            feedService.patchFeed(feedId, requestDto);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exception raised in SeverFeedController/updateFeed");
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @DeleteMapping("/feed/{feedId}")
    public void deleteFeed (@PathVariable("feedId") Long feedId){
        feedService.deleteFeed(feedId);
    }
}