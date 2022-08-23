package com.onjung.onjung.user.service;

import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.exception.DuplicatedUserException;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.user.domain.Role;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.dto.UserRequestDto;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void saveUser(UserRequestDto userRequestDto){

        try {
            User user= User.builder()
                    .username(userRequestDto.getUsername())
                    .password(passwordEncoder.encode(userRequestDto.getPassword()))
                    .email(userRequestDto.getEmail())
                    .uuid(userRequestDto.getUuid())
                    .locationId(userRequestDto.getLocation_id())
                    .provider(userRequestDto.getProvider())
                    .profileImg(userRequestDto.getProfileImg())
                    .profileIntro(userRequestDto.getProfileIntro())
                    .phone(userRequestDto.getPhone())
                    .birth(userRequestDto.getBirth())
                    .university(userRequestDto.getUniversity())
                    .build();

            validateDuplicateMember(user);
            userRepository.save(user);
//            System.out.println("user = " + userRepository.findByUsername("username").get().getUsername());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void validateDuplicateMember(User user){
        Optional<User> findMember = userRepository.findByUsername(user.getUsername());
        if (findMember.isPresent()){
            throw new DuplicatedUserException();
        }
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers(){
        return userRepository.findAllUsers();
    }
}
