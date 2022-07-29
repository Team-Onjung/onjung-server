package com.onjung.onjung.common.config;

import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class WebFluxFilter implements org.springframework.web.server.WebFilter{

    @Override
    public Mono filter(ServerWebExchange serverWebExchange, WebFilterChain webFilterChain) {
        return Mono
                .delay(Duration.ofMillis(200))
                .then(
                        webFilterChain.filter(serverWebExchange)
                );
    }
}
