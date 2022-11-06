package com.onjung.onjung.feed.service;

import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.feed.domain.Category;
import com.onjung.onjung.feed.domain.ClientFeed;

import com.onjung.onjung.feed.domain.status.ItemStatus;
import com.onjung.onjung.feed.dto.ClientFeedRequestDto;
import com.onjung.onjung.feed.repository.ClientFeedRepository;
import com.onjung.onjung.item.repository.CategoryRepository;
import com.onjung.onjung.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
public class ClientFeedService {

    private final ClientFeedRepository clientFeedRepository;

    private final CategoryRepository categoryRepository;

    @Transactional
    @CachePut(value = "clientFeedCaching", key = "#feedId")
    public void lendFeed(Long feedId) throws Exception {
        Optional<ClientFeed> clientFeed = clientFeedRepository.findById(feedId);
        if (clientFeed.isPresent() && clientFeed.get().getStatus() == ItemStatus.STATUS_POSSIBLE) {
            User LentUser = clientFeed.get().getWriter();
            LentUser.earnPoints();

            clientFeed.get().changeStatus(ItemStatus.STATUS_RESERVED);
            clientFeedRepository.save(clientFeed.get());
        } else {
            throw new DataNotFoundException();
        }
    }

    @Transactional
    @CacheEvict(value = "clientFeedCaching", allEntries = true)
    public void createFeed(ClientFeedRequestDto feedRequestDto, User feedUser) throws Exception {

        Category requestCategory = categoryRepository.findById(feedRequestDto.getCategoryId()).get();

        ClientFeed feed = ClientFeed.builder()
                .writer(feedUser)
                .title(feedRequestDto.getTitle())
                .content(feedRequestDto.getContent())
                .category(requestCategory)
                .startDate(feedRequestDto.getStartDate())
                .endDate(feedRequestDto.getEndDate())
                .duration(feedRequestDto.getDuration())
                .image(feedRequestDto.getImage())
                .build();

        clientFeedRepository.save(feed);
    }

    @Transactional(readOnly = true)
    @Cacheable("clientFeedCaching")
    @Async
    public Future<List<ClientFeed>> readAllFeed(){
        return new AsyncResult<List<ClientFeed>>(clientFeedRepository.findAll());
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "clientFeedCaching", key = "#feedId")
    @Async
    public Future<ClientFeed> readFeed(Long feedId) throws InterruptedException {
        Optional<ClientFeed> feed=clientFeedRepository.findById(feedId);
        if (feed.isPresent()){
            return new AsyncResult<ClientFeed>(feed.get());
        }else {
            throw new DataNotFoundException();
        }
    }

    @Transactional
    @CachePut(value = "clientFeedCaching", key = "#feedId")
    public ClientFeed putFeed(Long feedId, ClientFeedRequestDto feedRequestDto){
        try{
            ClientFeed feed = clientFeedRepository.findById(feedId).get();

            Category requestCategory = categoryRepository.findById(feedRequestDto.getCategoryId()).get();

            feed.setCategory(requestCategory);
            feed.setTitle(feedRequestDto.getTitle());
            feed.setStartDate(feedRequestDto.getStartDate());
            feed.setEndDate(feedRequestDto.getEndDate());
            feed.setDuration(feedRequestDto.getDuration());
            feed.setContent(feedRequestDto.getContent());
            feed.setImage(feedRequestDto.getImage());

            clientFeedRepository.save(feed);

            return feed;

        }catch(NoSuchElementException e){
            throw new DataNotFoundException();
        }
    }

    @Transactional
    @CacheEvict(value = "clientFeedCaching", allEntries = true)
    public void deleteFeed(Long feedId){
        Optional<ClientFeed> clientFeed=clientFeedRepository.findById(feedId);
        if(clientFeed.isPresent()){
            clientFeedRepository.delete(clientFeed.get());
        }else{
            throw new DataNotFoundException();
        }
    }

    @Transactional
    @CacheEvict(value = "clientFeedCaching", allEntries = true)
     public List<ClientFeed> getFeedOrderByCmd (String cmd){
        List<ClientFeed> clientFeedList = new ArrayList<ClientFeed>();
        switch (cmd) {
            case "price":
                clientFeedList = clientFeedRepository.findAllOrderByPrice();
            case "recent":
                clientFeedList = clientFeedRepository.findAllOrderByCreatedAt();
            case "able":
                clientFeedList = clientFeedRepository.getFeedOrderByStatus(ItemStatus.STATUS_POSSIBLE);
            case "unable":
                clientFeedList = clientFeedRepository.getFeedOrderByStatus(ItemStatus.STATUS_FINISHED);
        }
        return clientFeedList;
    }
}