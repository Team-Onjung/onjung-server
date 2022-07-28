package com.onjung.onjung.feed.service;

import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.exception.InvalidParameterException;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import com.onjung.onjung.feed.repository.jpa.ServerFeedRepository;
import com.onjung.onjung.feed.repository.r2dbc.ClientFeedReactiveRepository;
import com.onjung.onjung.feed.repository.r2dbc.ServerFeedReactiveRepository;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServerFeedService implements FeedService{

    private final EhCacheCacheManager cacheManager;

    private final ServerFeedRepository serverFeedRepository;
    private final UserRepository userRepository;

    @Autowired
    ServerFeedReactiveRepository serverFeedReactiveRepository;

    @Transactional(value = "transactionManager")
    @CacheEvict(value = "serverFeedCaching", allEntries = true)
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
        //여기까지 이후 변경 필요.
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

    @Transactional(readOnly = true, value = "connectionFactoryTransactionManager")
    @Cacheable("serverFeedCaching")
    public Flux<ServerFeed> readAllFeed(){
        return serverFeedReactiveRepository.findAll();
    }

    @Transactional(readOnly = true, value = "connectionFactoryTransactionManager")
    @Cacheable(value = "serverFeedCaching", key = "#feedId")
    public Mono<ServerFeed> readFeed(Long feedId){
        Mono<ServerFeed> feed=serverFeedReactiveRepository.findById(feedId);
        if (feed!=null){
            return feed;
        }else {
            throw new DataNotFoundException();
        }
    }

    @Transactional
    @CachePut(value = "serverFeedCaching", key = "#feedId")
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

    @Transactional
    @CacheEvict(value = "serverFeedCaching", allEntries = true)
    public void deleteFeed(Long feedId){
        Optional<ServerFeed> serverFeed=serverFeedRepository.findById(feedId);
        if(serverFeed.isPresent()){
            serverFeedRepository.delete(serverFeed.get());
        }
    }
}
