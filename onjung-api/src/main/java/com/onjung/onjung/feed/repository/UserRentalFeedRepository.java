package com.onjung.onjung.feed.repository;

import com.onjung.onjung.feed.domain.UserRentalFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRentalFeedRepository extends JpaRepository<UserRentalFeed,Long> {
    Optional<UserRentalFeed> findById(Long id);
}
