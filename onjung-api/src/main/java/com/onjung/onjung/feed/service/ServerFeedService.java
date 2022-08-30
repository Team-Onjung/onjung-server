package com.onjung.onjung.feed.service;

import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.feed.domain.Feed;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.domain.Status;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import com.onjung.onjung.feed.repository.ServerFeedRepository;
import com.onjung.onjung.item.domain.Item;
import com.onjung.onjung.item.repository.ItemRepository;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class ServerFeedService implements FeedService{

    private final ServerFeedRepository serverFeedRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Transactional
    @CachePut(value = "clientFeedCaching", key = "#feedId")
    public void borrowFeed(Long feedId) throws Exception {
        Optional<ServerFeed> serverFeed=serverFeedRepository.findById(feedId);
            if(serverFeed.isPresent() && serverFeed.get().getStatus()==Status.STATUS_POSSIBLE){
                User borrowedUser=serverFeed.get().getWriter();
                borrowedUser.discountPoints();

                serverFeed.get().changeStatus(Status.STATUS_RESERVED);
                serverFeedRepository.save(serverFeed.get());
            }else {
                throw new DataNotFoundException();
            }

    }

    @Transactional
    public void createFeed(FeedRequestDto feedRequestDto, User feedUser) throws Exception {

            Item requestItem = itemRepository.findById(feedRequestDto.getItemId()).get();

            ServerFeed feed = ServerFeed.builder()
                    .writer(feedUser)
                    .title(feedRequestDto.getTitle())
                    .body(feedRequestDto.getBody())
                    .item(requestItem)
                    .build();
            serverFeedRepository.save(feed);

    }

    @Async
    public Future<List<ServerFeed>> readAllFeed(){
        return new AsyncResult<List<ServerFeed>>(serverFeedRepository.findAll());
    }

    @Async
    public Future<ServerFeed> readFeed(Long feedId){
        Optional<ServerFeed> feed=serverFeedRepository.findById(feedId);
        if (feed.isPresent()){
            return new AsyncResult<ServerFeed>(feed.get());
        }else {
            throw new DataNotFoundException();
        }
    }

    public Feed patchFeed(Long feedId, FeedRequestDto requestDto){
        final Optional<ServerFeed> serverFeed= serverFeedRepository.findById(feedId);
        if(serverFeed.isPresent()){
            if(requestDto.getTitle()!=null){
                serverFeed.get().setTitle(requestDto.getTitle());
            }
            if(requestDto.getBody()!=null){
                serverFeed.get().setBody(requestDto.getBody());
            }
            if(requestDto.getItemId()!=null){
                Item requestItem = itemRepository.findById(requestDto.getItemId()).get();
                serverFeed.get().setItem(requestItem);
            }
            serverFeedRepository.save(serverFeed.get());
            return serverFeed.get();
        }else {
            throw new DataNotFoundException();
        }
    }

    public void deleteFeed(Long feedId){
        Optional<ServerFeed> serverFeed=serverFeedRepository.findById(feedId);
        if(serverFeed.isPresent()){
            serverFeedRepository.delete(serverFeed.get());
        }else{
            throw new DataNotFoundException();
        }
    }
}