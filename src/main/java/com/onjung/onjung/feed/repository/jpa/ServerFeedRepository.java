package com.onjung.onjung.feed.repository.jpa;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServerFeedRepository extends JpaRepository<ServerFeed,Long>{

    Optional<ServerFeed> findById(Long id);

    Optional<ServerFeed> findByTitle(String title);
}
