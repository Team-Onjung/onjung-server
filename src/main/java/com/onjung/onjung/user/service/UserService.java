package com.onjung.onjung.user.service;

import com.onjung.onjung.exception.DuplicatedUserException;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public void saveUser(User user){
        validateDuplicateMember(user);
        userRepository.save(user);
    }

    public void validateDuplicateMember(User user){
        Optional<User> findMember = userRepository.findByEmail(user.getEmail());
        if (findMember.isPresent()){
            throw new DuplicatedUserException();
        }
    }

}
