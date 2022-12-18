package com.onjung.onjung.feed.controller;

import com.onjung.onjung.common.auth.PrincipalDetails;
import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.exception.UnauthorizedException;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.dto.ClientFeedRequestDto;
import com.onjung.onjung.feed.repository.ClientFeedRepository;
import com.onjung.onjung.feed.service.ClientFeedService;
import com.onjung.onjung.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/client/feed")
public class ClientFeedController{

    private final ClientFeedService feedService;
    private final ClientFeedRepository feedRepository;

    @PostMapping("")
    public ResponseEntity createFeed(@RequestBody @Valid ClientFeedRequestDto requestDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        try {
            User user = principalDetails.getUser();
            feedService.createFeed(requestDto, user);
            return ResponseEntity.status(HttpStatus.OK).body("ok");
        } catch (NullPointerException e) {
            log.error(e.getMessage());
            throw new UnauthorizedException();
        }
    }

    @GetMapping("")
    public List<ClientFeed> readAllFeed() {
        return feedService.readAllFeed();
    }

    @PostMapping("{feedId}")
    public ResponseEntity lendFeed(@PathVariable("feedId") Long feedId) {
        feedService.lendFeed(feedId);
        return ResponseEntity.status(HttpStatus.OK).body("대여에 성공했습니다.");
    }

    @GetMapping("{feedId}")
    public ResponseEntity readFeed(@PathVariable("feedId") Long feedId) {
            ClientFeed feed = feedService.readFeed(feedId);
            return ResponseEntity.status(HttpStatus.OK).body(feed);
    }

    @PatchMapping("{feedId}")
    public ResponseEntity updateFeed(@PathVariable("feedId") Long feedId, @RequestBody @Valid ClientFeedRequestDto requestDto, @AuthenticationPrincipal PrincipalDetails principalDetails) throws DataNotFoundException {
        User user = principalDetails.getUser();
        feedService.putFeed(feedId, requestDto, user);
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @DeleteMapping("{feedId}")
    public ResponseEntity deleteFeed (@PathVariable("feedId") Long feedId, @AuthenticationPrincipal PrincipalDetails principalDetails) throws DataNotFoundException {
        User user = principalDetails.getUser();
        ClientFeed feed = feedRepository.findById(feedId).orElseThrow(DataNotFoundException::new);
        if (user.equals(feed.getWriter())) {
            feedService.deleteFeed(feedId);
            return ResponseEntity.status(HttpStatus.OK).body("ok");
        }
        throw new UnauthorizedException("해당 피드의 삭제 권한이 없습니다");
    }

    @GetMapping("filter/")
    public ResponseEntity<List<ClientFeed>> readFeedOrdered (@RequestParam("cmd") String cmd) {
        List<ClientFeed> feedOrderByCmd = feedService.getFeedOrderByCmd(cmd);
        return ResponseEntity.status(HttpStatus.OK).body(feedOrderByCmd);
    }

    /**
     * 카테고리별 피드 필터링
     */
    @GetMapping("category/{categoryId}")
    public ResponseEntity likeFeed (@PathVariable("categoryId") Long categoryId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if (principalDetails.isEnabled()) {
            feedService.getFeedByCategory(categoryId);
            return ResponseEntity.status(HttpStatus.OK).body(null);
        };
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증되지 않은 사용자입니다.");
    }
}