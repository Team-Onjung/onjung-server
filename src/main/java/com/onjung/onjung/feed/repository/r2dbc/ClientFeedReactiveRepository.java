package com.onjung.onjung.feed.repository.r2dbc;

import com.onjung.onjung.feed.domain.ClientFeed;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ClientFeedReactiveRepository extends R2dbcRepository<ClientFeed,Long> {

//    @Query("select * from client_feed")
    Mono<ClientFeed> findById(Long id);
}
