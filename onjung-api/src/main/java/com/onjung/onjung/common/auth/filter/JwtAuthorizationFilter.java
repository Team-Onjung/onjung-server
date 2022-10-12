package com.onjung.onjung.common.auth.filter;

import com.onjung.onjung.common.auth.application.TokenProvider;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter  extends BasicAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private TokenProvider tokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader(TokenProvider.HEADER_STRING);
        if(header == null || !header.startsWith(TokenProvider.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        String token = request.getHeader(TokenProvider.HEADER_STRING)
                .replace(TokenProvider.HEADER_STRING, "");
        tokenProvider.getAuthentication(token);
        chain.doFilter(request, response);
    }
}
