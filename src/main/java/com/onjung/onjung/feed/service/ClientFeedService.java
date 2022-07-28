package com.onjung.onjung.feed.service;

import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.exception.InvalidParameterException;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import com.onjung.onjung.feed.repository.jpa.ClientFeedRepository;
import com.onjung.onjung.feed.repository.r2dbc.ClientFeedReactiveRepository;
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
public class ClientFeedService implements FeedService{

    private final EhCacheCacheManager cacheManager;

    private final ClientFeedRepository clientFeedRepository;
    private final UserRepository userRepository;

    @Autowired
    ClientFeedReactiveRepository clientFeedReactiveRepository;

    @Transactional(value = "transactionManager")
    @CacheEvict(value = "clientFeedCaching", allEntries = true)
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
            ClientFeed feed = ClientFeed.builder()
                    .writer(savedUser.get())
                    .title(feedRequestDto.getTitle())
                    .body(feedRequestDto.getBody())
                    .itemId(feedRequestDto.getItemId())
                    .build();

            clientFeedRepository.save(feed);
        }catch (IllegalArgumentException e){
            throw new InvalidParameterException();
        }
    }

    @Transactional(readOnly = true, value = "connectionFactoryTransactionManager")
    @Cacheable("clientFeedCaching")
    public Flux<ClientFeed> readAllFeed(){
        return clientFeedReactiveRepository.findAll();
    }


    @Transactional(readOnly = true, value = "connectionFactoryTransactionManager")
    @Cacheable(value = "clientFeedCaching", key = "#feedId")
    public Mono<ClientFeed> readFeed(Long feedId) throws InterruptedException {
        Mono<ClientFeed> feed=clientFeedReactiveRepository.findById(feedId);
        if (feed!=null){
            return feed;
        }else {
            throw new DataNotFoundException();
        }
    }

    @Transactional
    @CachePut(value = "clientFeedCaching", key = "#feedId")
    public void patchFeed(Long feedId, FeedRequestDto requestDto){
        final Optional<ClientFeed> clientFeed= clientFeedRepository.findById(feedId);
        try {
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

        }catch (IllegalArgumentException e){
            throw new InvalidParameterException();
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
