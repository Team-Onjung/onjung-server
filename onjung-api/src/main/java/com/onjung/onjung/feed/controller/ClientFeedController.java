package com.onjung.onjung.feed.controller;

import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.exception.InvalidParameterException;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.dto.ClientFeedRequestDto;
import com.onjung.onjung.feed.repository.ClientFeedRepository;
import com.onjung.onjung.feed.service.ClientFeedService;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client/feed")
public class ClientFeedController{

    private final ClientFeedService feedService;
    private final ClientFeedRepository feedRepository;
    private final UserRepository userRepository;

    @PostMapping("")
    public ResponseEntity createFeed(@RequestBody @Valid ClientFeedRequestDto requestDto, BindingResult result) throws Exception{
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if ((String)principal!= "anonymousUser") {
            Optional<User> _user = userRepository.findByUsername((String) principal);

            if (_user.isPresent()) {
                User user = _user.get();

                feedService.createFeed(requestDto, user);

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

    @GetMapping("")
    public List<ClientFeed> readAllFeed() throws ExecutionException, InterruptedException, TimeoutException {
        return feedService.readAllFeed().get();
    }

    @PostMapping("{feedId}")
    public ResponseEntity lendFeed(@PathVariable("feedId") Long feedId) throws Exception{
            feedService.lendFeed(feedId);
            return ResponseEntity.status(HttpStatus.OK).body("lending is succeed");
    }

    @GetMapping("{feedId}")
    public ResponseEntity readFeed(@PathVariable("feedId") Long feedId) throws ExecutionException, TimeoutException, InterruptedException {
        ClientFeed feed = feedService.readFeed(feedId).get(200L, TimeUnit.MILLISECONDS);
        return ResponseEntity.status(HttpStatus.OK).body(feed);
    }

    @PatchMapping("{feedId}")
    public ResponseEntity updateFeed(@PathVariable("feedId") Long feedId, @RequestBody @Valid ClientFeedRequestDto requestDto, BindingResult result) throws DataNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if ((String)principal!= "anonymousUser") {
            Optional<User> _user = userRepository.findByUsername((String) principal);
            Optional<ClientFeed> _feed = feedRepository.findById(feedId);

            if (_user.isPresent() && _user.get().equals(_feed.get().getWriter())) {
                feedService.putFeed(feedId, requestDto);

                if (result.hasErrors()) {
                    throw new InvalidParameterException(result);
                }
                return ResponseEntity.status(HttpStatus.OK).body("ok");

            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("you can not patch feed that you did not write");
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("you need to login");
        }
    }

    @DeleteMapping("{feedId}")
    public ResponseEntity deleteFeed (@PathVariable("feedId") Long feedId) throws DataNotFoundException {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if ((String)principal!= "anonymousUser") {
            Optional<User> _user = userRepository.findByUsername((String) principal);
            Optional<ClientFeed> _feed = feedRepository.findById(feedId);

            if (_user.isPresent() && _user.get().equals(_feed.get().getWriter())) {
                feedService.deleteFeed(feedId);
                return ResponseEntity.status(HttpStatus.OK).body("ok");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("you can not delete feed that you did not write");
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("you need to login");
        }
    }

    @GetMapping("filter/")
    public ResponseEntity readFeedOrdered (@RequestParam("cmd") String cmd){
        List<ClientFeed> feedOrderByCmd = feedService.getFeedOrderByCmd(cmd);
        return ResponseEntity.status(HttpStatus.OK).body(feedOrderByCmd);
    }
}