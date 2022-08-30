package com.onjung.onjung.user.repository;

import com.onjung.onjung.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

//    @Query("select a from User a join fetch a.clientFeedList")
//    List<User> findAllUsers();
}