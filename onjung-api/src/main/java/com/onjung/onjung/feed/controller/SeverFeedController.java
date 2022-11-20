package com.onjung.onjung.feed.controller;

import com.onjung.onjung.common.auth.PrincipalDetails;
import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.exception.InvalidParameterException;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.dto.ServerFeedRequestDto;
import com.onjung.onjung.feed.service.ServerFeedService;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/server/feed")
public class SeverFeedController {

    private final ServerFeedService feedService;
    private final UserRepository userRepository;

    @PostMapping("")
    public ResponseEntity createFeed(@RequestBody @Valid ServerFeedRequestDto requestDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        User user = principalDetails.getUser();
        feedService.createFeed(requestDto, user);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @GetMapping("")
    public List<ServerFeed> readAllFeed() {
        return feedService.readAllFeed();
    }

    @PostMapping("/{feedId}")
    public ResponseEntity borrowFeed(@PathVariable("feedId") Long feedId) {
        feedService.borrowFeed(feedId);
        return ResponseEntity.status(HttpStatus.OK).body("borrowing is succeed");
    }

    @GetMapping("/{feedId}")
    public ResponseEntity readFeed(@PathVariable("feedId") Long feedId) {
        ServerFeed feed = feedService.readFeed(feedId);
        return ResponseEntity.status(HttpStatus.OK).body(feed);
    }

    @PatchMapping("/{feedId}")
    public ResponseEntity updateFeed(@PathVariable("feedId") Long feedId, @RequestBody @Valid ServerFeedRequestDto requestDto, @AuthenticationPrincipal PrincipalDetails principalDetails, BindingResult result) throws DataNotFoundException {
        User user = principalDetails.getUser();
        feedService.putFeed(feedId, requestDto, user);
        if (result.hasErrors()) {
            throw new InvalidParameterException(result);
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @DeleteMapping("/{feedId}")
    public ResponseEntity deleteFeed (@PathVariable("feedId") Long feedId){
        feedService.deleteFeed(feedId);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @GetMapping("filter/")
    public ResponseEntity readFeedOrdered (@RequestParam("cmd") String cmd){
        List<ServerFeed> feedOrderByCmd = feedService.getFeedOrderByCmd(cmd);
        return ResponseEntity.status(HttpStatus.OK).body(feedOrderByCmd);
    }
}