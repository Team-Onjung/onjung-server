package com.onjung.onjung.user.service;

import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.user.domain.Role;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        System.out.println("username = " + username);
        Optional<User> _loginUser = userRepository.findByUsername(username);
        if (_loginUser.isEmpty()) {
            throw new DataNotFoundException();
        }
//        System.out.println("_loginUser = " + _loginUser.get());

        User loginUser = _loginUser.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.getValue()));
        }
        return new org.springframework.security.core.userdetails.User(loginUser.getUsername(), loginUser.getPassword(), authorities);
    }
}
