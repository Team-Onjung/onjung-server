package com.onjung.onjung.feed.repository.r2dbc;

import com.onjung.onjung.feed.domain.ClientFeed;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

public interface reactiveRepository extends R2dbcRepository<ClientFeed,Long> {

//    @Query("select * from client_feed")
    Mono<ClientFeed> findById(Long id);
}
