 package com.onjung.onjung.feed.service;

import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.exception.InvalidParameterException;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.Status;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import com.onjung.onjung.feed.repository.ClientFeedRepository;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientFeedService implements FeedService{

    private final ClientFeedRepository clientFeedRepository;

    @Transactional
    @CachePut(value = "clientFeedCaching", key = "#feedId")
    public void lendFeed(Long feedId) throws Exception {
        Optional<ClientFeed> clientFeed=clientFeedRepository.findById(feedId);
        try {
            if(clientFeed.isPresent() && clientFeed.get().getStatus()==Status.STATUS_POSSIBLE){
                User LentUser=clientFeed.get().getWriter();
                LentUser.earnPoints();

                clientFeed.get().changeStatus(Status.STATUS_RESERVED);
                clientFeedRepository.save(clientFeed.get());
            }else {
                throw new DataNotFoundException();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    @CacheEvict(value = "clientFeedCaching", allEntries = true)
    public void createFeed(FeedRequestDto feedRequestDto, User feedUser) throws Exception {


            ClientFeed feed = ClientFeed.builder()
                    .writer(feedUser)
                    .title(feedRequestDto.getTitle())
                    .body(feedRequestDto.getBody())
                    .itemId(feedRequestDto.getItemId())
                    .build();

            clientFeedRepository.save(feed);

    }

    @Transactional(readOnly = true)
    @Cacheable("clientFeedCaching")
    @Async
    public List<ClientFeed> readAllFeed(){
        return clientFeedRepository.findAll();
    }


    @Transactional(readOnly = true)
    @Cacheable(value = "clientFeedCaching", key = "#feedId")
    @Async
    public Optional<ClientFeed> readFeed(Long feedId) throws InterruptedException {
//        Thread.sleep(3000);
        Optional<ClientFeed> feed=clientFeedRepository.findById(feedId);
        if (feed.isPresent()){
            return feed;
        }else {
            throw new DataNotFoundException();
        }
    }

    @Transactional
    @CachePut(value = "clientFeedCaching", key = "#feedId")
    public void patchFeed(Long feedId, FeedRequestDto requestDto){
        final Optional<ClientFeed> clientFeed= clientFeedRepository.findById(feedId);
            if(clientFeed.isPresent()){
                if(requestDto.getTitle()!=null){
                    clientFeed.get().setTitle(requestDto.getTitle());
                }
                if(requestDto.getBody()!=null){
                    clientFeed.get().setBody(requestDto.getBody());
                }
                if(requestDto.getItemId()!=null){
                    clientFeed.get().setItemId(requestDto.getItemId());
                }
                clientFeedRepository.save(clientFeed.get());
            }else {
                throw new DataNotFoundException();
            }


    }

    @Transactional
    @CacheEvict(value = "clientFeedCaching", allEntries = true)
    public void deleteFeed(Long feedId){
        Optional<ClientFeed> clientFeed=clientFeedRepository.findById(feedId);
        if(clientFeed.isPresent()){
            clientFeedRepository.delete(clientFeed.get());
        }
    }
}