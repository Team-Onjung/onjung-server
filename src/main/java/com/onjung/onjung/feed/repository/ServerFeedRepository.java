package com.onjung.onjung.feed.repository;

import com.onjung.onjung.feed.domain.ServerFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerFeedRepository extends JpaRepository<ServerFeed,Long>{
}
