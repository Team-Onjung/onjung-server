package com.onjung.onjung.feed.repository;

import com.onjung.onjung.feed.domain.ServerFeed;

import com.onjung.onjung.feed.domain.status.ItemStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ServerFeedRepository extends JpaRepository<ServerFeed,Long>{

    Optional<ServerFeed> findById(Long id);

    Optional<ServerFeed> findByTitle(String title);

    //    List<ServerFeed> findAllOrderByPrice();
    @Query("SELECT f from ServerFeed f order by  f.createdAt")
    List<ServerFeed> findAllOrderByCreatedAt();

    @Query("SELECT f from ServerFeed f where f.status = :status order by  f.createdAt")
    List<ServerFeed> getFeedOrderByStatus(@Param("status") ItemStatus status);
}
