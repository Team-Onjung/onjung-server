package com.onjung.onjung.common.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.onjung.onjung.common.auth.PrincipalDetails;
import com.onjung.onjung.common.auth.application.TokenProvider;
import com.onjung.onjung.exception.UnauthorizedException;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * login 요청(post) 시 username(email), password 전송하면
 * UsernamePasswordAuthenticationFilter 동작
 */
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;
    private ObjectMapper mapper = new ObjectMapper();

    private final UserRepository userRepository;

    /**
     * login 요청을 하면 로그인 시도를 위해서 실행 -> email, password 확인 작업
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        try {
            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(), User.class);

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * attemptAuthentication 실행 후 인증이 정상적으로 된 경우 아래 함수 실행
     * JWT 토큰 발급 -> request한 사용자에게 JWT response body에 담아 보내기
     */

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        System.out.println("!!!!!");
        System.out.println(authResult);
        System.out.println(authResult.getPrincipal().toString());
        Optional<User> finduser= userRepository.findByUsername(authResult.getPrincipal().toString());
        System.out.println("finduser = " + finduser.isPresent());

//        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        PrincipalDetails principalDetails = new PrincipalDetails(finduser.get());

        User user = principalDetails.getUser();

        System.out.println("user = " + user);

        String jwtToken = tokenProvider.createToken(user);
        Map<String, String> json = new HashMap<>();
        json.put("msg", "정상적으로 토큰이 발급되었습니다");
        json.put("token", jwtToken);
        String jsonResponse = mapper.writeValueAsString(ResponseEntity.status(200).body(json));

        System.out.println("jsonResponse = " + jsonResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(jsonResponse);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        throw new UnauthorizedException();
    }
}