package com.onjung.onjung.feed.service;

import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.feed.domain.*;
import com.onjung.onjung.feed.dto.ServerFeedRequestDto;
import com.onjung.onjung.feed.repository.ServerFeedRepository;
import com.onjung.onjung.item.repository.CategoryRepository;
import com.onjung.onjung.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ServerFeedService {
    private final ServerFeedRepository serverFeedRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @CachePut(value = "clientFeedCaching", key = "#feedId")
    public void borrowFeed(Long feedId) {
        ServerFeed serverFeed = serverFeedRepository.findById(feedId).orElseThrow(DataNotFoundException::new);
        if(serverFeed.isPossible()){
            serverFeed.getWriter().discountPoints();
            serverFeed.changeStatus(Status.STATUS_RESERVED);
            serverFeedRepository.save(serverFeed);
        } else {
            throw new DataNotFoundException();
        }
    }

    @Transactional
    public void createFeed(ServerFeedRequestDto feedRequestDto, User feedUser) {
        Category requestCategory = categoryRepository.findById(feedRequestDto.getCategoryId()).orElseThrow(() -> new DataNotFoundException("카테고리가 존재하지 않습니다."));
        ServerFeed feed = feedRequestDto.toEntity(feedUser, requestCategory);
        serverFeedRepository.save(feed);
    }

    public List<ServerFeed> readAllFeed(){
        return serverFeedRepository.findAll();
    }

    public ServerFeed readFeed(Long feedId){
        ServerFeed feed = serverFeedRepository.findById(feedId).orElseThrow(DataNotFoundException::new);
        feed.addAccessCnt();
        serverFeedRepository.save(feed);
        return feed;
    }

    public Feed putFeed(Long feedId, ServerFeedRequestDto feedRequestDto, User user){
        ServerFeed feed = serverFeedRepository.findById(feedId).orElseThrow(() -> new DataNotFoundException("피드가 존재하지 않습니다."));
        Category requestCategory = categoryRepository.findById(feedRequestDto.getCategoryId()).orElseThrow(() -> new DataNotFoundException("카테고리가 존재하지 않습니다."));
        if (user.getEmail().equals(feed.getWriter().getEmail())) {
            serverFeedRepository.save(feedRequestDto.updatedEntity(feed, requestCategory));
        }
        return feed;
    }

    public void deleteFeed(Long feedId){
        ServerFeed feed = serverFeedRepository.findById(feedId).orElseThrow(DataNotFoundException::new);
        serverFeedRepository.delete(feed);
    }

    public List<ServerFeed> getFeedOrderByCmd (String cmd){
        List<ServerFeed> serverFeedList = new ArrayList<ServerFeed>();
        switch (cmd) {
            case "price":
                serverFeedList = serverFeedRepository.findAllOrderByRentalFee();
                break;
            case "recent":
                serverFeedList = serverFeedRepository.findAllOrderByCreatedAt();
                break;
            case "able":
                serverFeedList = serverFeedRepository.getFeedOrderByStatus(Status.STATUS_POSSIBLE);
                break;
            case "unable":
                serverFeedList = serverFeedRepository.getFeedOrderByStatus(Status.STATUS_FINISHED);
                break;
            default:
                throw new DataNotFoundException(cmd + "는 잘못된 cmd 입니다. (가능한 cmd = {price : 가격, recent : 최신순, able : 대여가능 상태, unable : 대여 불가능 상태} )");
        }
        return serverFeedList;
    }
}