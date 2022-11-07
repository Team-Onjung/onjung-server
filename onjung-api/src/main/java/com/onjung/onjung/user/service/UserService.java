package com.onjung.onjung.user.service;

import com.onjung.onjung.exception.DuplicatedUserException;
import com.onjung.onjung.feed.repository.UserRentalFeedRepository;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.dto.AdditionalInfoRequestDTO;
import com.onjung.onjung.user.dto.UserRequestDto;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void collectAdditionalInfo(AdditionalInfoRequestDTO additionalInfoRequestDTO){
        //유저정보를 받아왔다 가정.
        User testUser=userRepository.findById(1L).get();

        User user=testUser;

        user.setFullName(additionalInfoRequestDTO.getFullName());
        user.setUniversity(additionalInfoRequestDTO.getUniversityName());
        user.setPhone(additionalInfoRequestDTO.getPhone());
        user.setProfileImg(additionalInfoRequestDTO.getProfileImg());

        userRepository.save(user);
    }

    @Transactional
    public void saveUser(UserRequestDto userRequestDto){

        try {
            User user= User.builder()
                    .username(userRequestDto.getUsername())
                    .password(passwordEncoder.encode(userRequestDto.getPassword()))
                    .email(userRequestDto.getEmail())
                    .uuid(userRequestDto.getUuid())
                    .provider(userRequestDto.getProvider())
                    .profileImg(userRequestDto.getProfileImg())
                    .profileIntro(userRequestDto.getProfileIntro())
                    .phone(userRequestDto.getPhone())
                    .birth(userRequestDto.getBirth())
                    .locationId(userRequestDto.getLocationId())
                    .university(userRequestDto.getUniversity())
                    .build();

            validateDuplicateMember(user);
            userRepository.save(user);
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
