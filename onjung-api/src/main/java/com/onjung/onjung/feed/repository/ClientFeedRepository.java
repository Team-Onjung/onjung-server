package com.onjung.onjung.feed.repository;

import com.onjung.onjung.feed.domain.ClientFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientFeedRepository extends JpaRepository<ClientFeed,Long> {

    Optional<ClientFeed> findById(Long id);

    Optional<ClientFeed> findByTitle(String title);

//    List<Optional<ClientFeed>> findALLByWriter(Long id);
}
