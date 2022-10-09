package com.onjung.onjung.common.auth.application;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.onjung.onjung.common.auth.PrincipalDetails;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class TokenProvider {
    public static final String HEADER_STRING = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    private final String secret;
    private final long tokenValidityInMilliseconds;
    @Autowired private UserRepository userRepository;

    public TokenProvider(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
    }

    /**
     * 토큰 생성 메서드
     */
    public String createToken(User user) {
        String jwtToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + tokenValidityInMilliseconds))
                .withClaim("id", user.getId())
                .withClaim("username", user.getUsername())
                .withClaim("email", user.getEmail())
                .sign(Algorithm.HMAC512(secret));
        return TOKEN_PREFIX + jwtToken;
    }

    /*
		 Token에 담겨있는 정보를 이용해 Authentication 객체를 반환하는 메서드
	 */

    public void getAuthentication(String JwtToken) {
        String token = JwtToken.replace(TOKEN_PREFIX, "");
        String email = JWT.require(Algorithm.HMAC512(secret)).build().verify(token)
                .getClaim("email").asString();

        if(email != null) {
            Optional<User> userEntity = userRepository.findByEmail(email);
            try{
                User user = userEntity.get();
                PrincipalDetails principalDetails = new PrincipalDetails(user);
                Authentication authentication =
                        new UsernamePasswordAuthenticationToken(
                                principalDetails,
                                null,
                                principalDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (NullPointerException e) {
            }
        }
    }

    public String getSecret() {
        return secret;
    }
}