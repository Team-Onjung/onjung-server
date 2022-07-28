package com.onjung.onjung.feed.repository.r2dbc;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ServerFeedReactiveRepository extends R2dbcRepository<ServerFeed,Long> {

//    @Query("select * from client_feed")
    Mono<ServerFeed> findById(Long id);
}
