package com.onjung.onjung.feed.service;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clientfeed")
public class webfluxController {

    @Autowired
    private webfluxService webfluxService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ClientFeed> create(@RequestBody ClientFeed feed){
        return webfluxService.createUser(feed);
    }

    @GetMapping
    public Flux<ClientFeed> getAllUsers(){
        return webfluxService.getAllUsers();
    }

    @GetMapping("/{feedId}")
    public Mono<ResponseEntity<ClientFeed>> getUserById(@PathVariable Long feedId){
        Mono<ClientFeed> user = webfluxService.findById(feedId);
        return user.map( u -> ResponseEntity.ok(u))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

}
