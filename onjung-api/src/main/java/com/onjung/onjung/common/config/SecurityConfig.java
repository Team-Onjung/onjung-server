package com.onjung.onjung.common.config;

import com.onjung.onjung.common.auth.DummyAuthenticationManager;
import com.onjung.onjung.common.auth.filter.JwtAuthenticationFilter;
import com.onjung.onjung.common.auth.filter.JwtAuthorizationFilter;
import com.onjung.onjung.exception.ForbiddenException;
import com.onjung.onjung.exception.UnauthorizedException;
import com.onjung.onjung.common.auth.application.TokenProvider;
import com.onjung.onjung.user.repository.UserRepository;
import com.onjung.onjung.user.service.UserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{

    private final UserSecurityService userSecurityService;
    private final TokenProvider tokenProvider;
    private final ForbiddenException forbiddenException;
    private final UnauthorizedException unauthorizedException;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        AuthenticationManager authentication = authenticationManager(authenticationConfiguration);
        AuthenticationManager authentication = authenticationManager();
        JwtAuthenticationFilter authenticationFilter = new JwtAuthenticationFilter(authentication, tokenProvider, userRepository);
        authenticationFilter.setFilterProcessesUrl("/user/login");

        http.csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedException)
                .accessDeniedHandler(forbiddenException)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                .formLogin().disable()
                .httpBasic().disable()

                .addFilter(authenticationFilter)
                .addFilter(new JwtAuthorizationFilter(authentication, tokenProvider))
                .authorizeRequests();

        http.authorizeRequests().antMatchers("user/**").permitAll()
                .and()
                .headers()
                .addHeaderWriter(new XFrameOptionsHeaderWriter(
                        XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN))
                .and()
                    .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                    .logoutSuccessUrl("/client/feed")
                    .invalidateHttpSession(true);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return new DummyAuthenticationManager();
    }
}
