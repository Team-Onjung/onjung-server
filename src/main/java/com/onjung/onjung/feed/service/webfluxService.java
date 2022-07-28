package com.onjung.onjung.feed.service;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.repository.r2dbc.reactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@Transactional
public class webfluxService {

    @Autowired
    reactiveRepository clientFeedRepository;

    public Mono<ClientFeed> createUser(ClientFeed feed){
        return clientFeedRepository.save(feed);
    }

    public Flux<ClientFeed> getAllUsers(){
        return clientFeedRepository.findAll();
    }

    public Mono<ClientFeed> findById(Long feedId){
        return clientFeedRepository.findById(feedId);
    }

}
