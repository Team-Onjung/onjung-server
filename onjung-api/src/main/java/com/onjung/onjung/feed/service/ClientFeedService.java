 package com.onjung.onjung.feed.service;

import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.exception.UnauthorizedException;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

 @Service
@RequiredArgsConstructor
public class ClientFeedService {

    private final ClientFeedRepository clientFeedRepository;

    private final CategoryRepository categoryRepository;

    @Transactional
    @CachePut(value = "clientFeedCaching", key = "#feedId")
    public void lendFeed(Long feedId) {
        ClientFeed clientFeed = clientFeedRepository.findById(feedId).orElseThrow(DataNotFoundException::new);
        if (clientFeed.isPossible()) {
            User feedWriter = clientFeed.getWriter();
            feedWriter.earnPoints();
            clientFeed.changeStatus(ItemStatus.STATUS_RESERVED);
            clientFeedRepository.save(clientFeed);
        } else {
            throw new DataNotFoundException();
        }
    }

    @Transactional
    @CacheEvict(value = "clientFeedCaching", allEntries = true)
    public void createFeed(ClientFeedRequestDto feedRequestDto, User feedUser) {
        Category requestCategory = categoryRepository.findById(feedRequestDto.getCategoryId())
                                    .orElseThrow(DataNotFoundException::new);
        ClientFeed feed = feedRequestDto.toEntity(feedUser, requestCategory);
        clientFeedRepository.save(feed);
    }

    @Transactional(readOnly = true)
    @Cacheable("clientFeedCaching")
    public List<ClientFeed> readAllFeed(){
        return clientFeedRepository.findAll();
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "clientFeedCaching", key = "#feedId")
    public ClientFeed readFeed(Long feedId) {
        ClientFeed feed = clientFeedRepository.findById(feedId).orElseThrow(DataNotFoundException::new);
        feed.addAccessCnt();
        clientFeedRepository.save(feed);
        return feed;
    }

    @Transactional
    @CachePut(value = "clientFeedCaching", key = "#feedId")
    public void putFeed(Long feedId, ClientFeedRequestDto feedRequestDto, User user){
        ClientFeed feed = clientFeedRepository.findById(feedId).orElseThrow(() -> new DataNotFoundException("피드가 존재하지 않습니다."));
        Category requestCategory = categoryRepository.findById(feedRequestDto.getCategoryId()).orElseThrow(() -> new DataNotFoundException("카테고리가 존재하지 않습니다."));
        if (user.getEmail().equals(feed.getWriter().getEmail())) {
            clientFeedRepository.save(feedRequestDto.updatedEntity(feed, requestCategory));
        } else {
            throw new UnauthorizedException("요청하신 피드 (id = " + feed.getId() + " )는 요청자의 소유가 아닙니다.");
        }
    }

    @Transactional
    @CacheEvict(value = "clientFeedCaching", allEntries = true)
    public void deleteFeed(Long feedId){
        ClientFeed clientFeed = clientFeedRepository.findById(feedId).orElseThrow(DataNotFoundException::new);
        clientFeedRepository.delete(clientFeed);
    }

    @Transactional
    @CacheEvict(value = "clientFeedCaching", allEntries = true)
     public List<ClientFeed> getFeedOrderByCmd (String cmd){
        List<ClientFeed> clientFeedList = new ArrayList<ClientFeed>();
        switch (cmd) {
            case "price":
                clientFeedList = clientFeedRepository.findAllOrderByPrice();
                break;
            case "recent":
                clientFeedList = clientFeedRepository.findAllOrderByCreatedAt();
                break;
            case "able":
                clientFeedList = clientFeedRepository.getFeedOrderByStatus(ItemStatus.STATUS_POSSIBLE);
                break;
            case "unable":
                clientFeedList = clientFeedRepository.getFeedOrderByStatus(ItemStatus.STATUS_FINISHED);
                break;
        }
        return clientFeedList;
    }
}