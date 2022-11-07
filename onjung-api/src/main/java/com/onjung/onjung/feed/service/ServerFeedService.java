package com.onjung.onjung.feed.service;

import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.feed.domain.Category;
import com.onjung.onjung.feed.domain.Feed;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.domain.status.ItemStatus;
import com.onjung.onjung.feed.domain.*;
import com.onjung.onjung.feed.dto.ServerFeedRequestDto;
import com.onjung.onjung.feed.repository.ServerFeedRepository;
import com.onjung.onjung.item.repository.CategoryRepository;
import com.onjung.onjung.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class ServerFeedService {

    private final ServerFeedRepository serverFeedRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @CachePut(value = "clientFeedCaching", key = "#feedId")
    public void borrowFeed(Long feedId) throws Exception {
        Optional<ServerFeed> serverFeed=serverFeedRepository.findById(feedId);
            if(serverFeed.isPresent() && serverFeed.get().getStatus()== ItemStatus.STATUS_POSSIBLE){
                User borrowedUser=serverFeed.get().getWriter();
                borrowedUser.discountPoints();

                serverFeed.get().changeStatus(ItemStatus.STATUS_RESERVED);
                serverFeedRepository.save(serverFeed.get());
            }else {
                throw new DataNotFoundException();
            }

    }

    @Transactional
    public void createFeed(ServerFeedRequestDto feedRequestDto, User feedUser) throws Exception {

        Category requestCategory = categoryRepository.findById(feedRequestDto.getCategoryId()).get();

        double requestComissionFee = feedRequestDto.getRentalFee() * 0.05;

            ServerFeed feed = ServerFeed.builder()
                    .writer(feedUser)
                    .title(feedRequestDto.getTitle())
                    .content(feedRequestDto.getContent())
                    .category(requestCategory)
                    .startDate(feedRequestDto.getStartDate())
                    .endDate(feedRequestDto.getEndDate())
                    .duration(feedRequestDto.getDuration())
                    .image(feedRequestDto.getImage())
                    .rentalFee(feedRequestDto.getRentalFee())
                    .deposit(feedRequestDto.getDeposit())
                    .commissionFee(requestComissionFee)
                    .build();

            serverFeedRepository.save(feed);

    }

    @Async
    public Future<List<ServerFeed>> readAllFeed(){
        return new AsyncResult<List<ServerFeed>>(
                serverFeedRepository.findAll()
        );
    }

    @Async
    public Future<ServerFeed> readFeed(Long feedId){
        Optional<ServerFeed> feed=serverFeedRepository.findById(feedId);
        if (feed.isPresent()){
            return new AsyncResult<ServerFeed>(feed.get());
        }else {
            System.out.println("여기!");
            throw new DataNotFoundException();
        }
    }

    public Feed putFeed(Long feedId, ServerFeedRequestDto feedRequestDto){
        try {
            ServerFeed feed = serverFeedRepository.findById(feedId).get();

            double requestCommissionFee = feedRequestDto.getRentalFee() * 0.05;

            Category requestCategory = categoryRepository.findById(feedRequestDto.getCategoryId()).get();

            feed.setCategory(requestCategory);
            feed.setContent(feedRequestDto.getContent());
            feed.setCommissionFee(requestCommissionFee);
            feed.setDeposit(feedRequestDto.getDeposit());
            feed.setDuration(feedRequestDto.getDuration());
            feed.setStartDate(feedRequestDto.getStartDate());
            feed.setEndDate(feedRequestDto.getEndDate());
            feed.setTitle(feedRequestDto.getTitle());
            feed.setImage(feedRequestDto.getImage());
            feed.setRentalFee(feedRequestDto.getRentalFee());

            serverFeedRepository.save(feed);

            return feed;

        }catch(NoSuchElementException e){
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

    public List<ServerFeed> getFeedOrderByCmd (String cmd){
        List<ServerFeed> serverFeedList = new ArrayList<ServerFeed>();
        switch (cmd) {
//            case "price":
//                serverFeedList = serverFeedRepository.findAllOrderByPrice();
            case "recent":
                serverFeedList = serverFeedRepository.findAllOrderByCreatedAt();
            case "able":
                serverFeedList = serverFeedRepository.getFeedOrderByStatus(ItemStatus.STATUS_POSSIBLE);
            case "unable":
                serverFeedList = serverFeedRepository.getFeedOrderByStatus(ItemStatus.STATUS_FINISHED);
        }
        return serverFeedList;
    }
}