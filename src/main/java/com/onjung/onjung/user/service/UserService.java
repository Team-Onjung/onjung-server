package com.onjung.onjung.user.service;

import com.onjung.onjung.exception.DuplicatedUserException;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.dto.UserRequestDto;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void saveUser(UserRequestDto userRequestDto){
        try {
            User user= User.builder()
                    .email(userRequestDto.getEmail())
                    .uuid(userRequestDto.getUuid())
                    .locationId(userRequestDto.getLocation_id())
                    .provider(userRequestDto.getProvider())
                    .profileImg(userRequestDto.getProfileImg())
                    .profileIntro(userRequestDto.getProfileIntro())
                    .phone(userRequestDto.getPhone())
                    .username(userRequestDto.getUsername())
                    .birth(userRequestDto.getBirth())
                    .university(userRequestDto.getUniversity())
                    .build();

            validateDuplicateMember(user);
            userRepository.save(user);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void validateDuplicateMember(User user){
        Optional<User> findMember = userRepository.findByEmail(user.getEmail());
        if (findMember.isPresent()){
            throw new DuplicatedUserException();
        }
    }

}
