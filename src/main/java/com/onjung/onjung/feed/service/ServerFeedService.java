package com.onjung.onjung.feed.service;

import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.exception.InvalidParameterException;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.domain.Status;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import com.onjung.onjung.feed.repository.ServerFeedRepository;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServerFeedService implements FeedService{

    private final ServerFeedRepository serverFeedRepository;
    private final UserRepository userRepository;

    @Transactional
    @CachePut(value = "clientFeedCaching", key = "#feedId")
    public void borrowFeed(Long feedId) throws Exception {
        Optional<ServerFeed> serverFeed=serverFeedRepository.findById(feedId);
        try {
            if(serverFeed.isPresent()){
                User borrowedUser=serverFeed.get().getWriter();
                borrowedUser.discountPoints();

                serverFeed.get().changeStatus(Status.STATUS_RESERVED);
                serverFeedRepository.save(serverFeed.get());
            }else {
                throw new DataNotFoundException();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void createFeed(FeedRequestDto feedRequestDto) throws Exception {
        //임시로 유저 객체 저장, 이후 회원가입한 회원에 한해 저장하는걸로 수정.
        LocalDate birthDate= LocalDate.ofYearDay(2022,1);
        String name=Double.toString(Math.random());

        User testUser=User.builder()
                .email("email")
                .birth(birthDate)
                .locationId("locationId")
                .phone("phone")
                .profileImg("profileImg")
                .profileIntro("profileIntro")
                .provider("provider")
                .university("university")
                .username(name)
                .uuid("uuid")
                .build();
        userRepository.save(testUser);

        Optional<User> savedUser= userRepository.findByUsername(name);

        try {
            ServerFeed feed = ServerFeed.builder()
                    .writer(savedUser.get())
                    .title(feedRequestDto.getTitle())
                    .body(feedRequestDto.getBody())
                    .itemId(feedRequestDto.getItemId())
                    .build();
            serverFeedRepository.save(feed);
        }catch (IllegalArgumentException e){
            throw new InvalidParameterException();
        }
    }

    public List<ServerFeed> readAllFeed(){
        return serverFeedRepository.findAll();
    }

    public Optional<ServerFeed> readFeed(Long feedId){
        Optional<ServerFeed> feed=serverFeedRepository.findById(feedId);
        if (feed.isPresent()){
            return feed;
        }else {
            throw new DataNotFoundException();
        }
    }

    public void patchFeed(Long feedId, FeedRequestDto requestDto){
        final Optional<ServerFeed> serverFeed= serverFeedRepository.findById(feedId);
        try {
            if(serverFeed.isPresent()){
                if(requestDto.getTitle()!=null){
                    serverFeed.get().setTitle(requestDto.getTitle());
                }
                if(requestDto.getBody()!=null){
                    serverFeed.get().setBody(requestDto.getBody());
                }
                if(requestDto.getItemId()!=null){
                    serverFeed.get().setItemId(requestDto.getItemId());
                }
                serverFeedRepository.save(serverFeed.get());
            }else {
                throw new DataNotFoundException();
            }

        }catch (IllegalArgumentException e){
            throw new InvalidParameterException();
        }
    }

    public void deleteFeed(Long feedId){
        Optional<ServerFeed> serverFeed=serverFeedRepository.findById(feedId);
        if(serverFeed.isPresent()){
            serverFeedRepository.delete(serverFeed.get());
        }
    }
}