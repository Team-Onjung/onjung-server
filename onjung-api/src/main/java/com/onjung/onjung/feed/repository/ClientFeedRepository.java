package com.onjung.onjung.feed.repository;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.status.ItemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientFeedRepository extends JpaRepository<ClientFeed,Long> {

    Optional<ClientFeed> findById(Long id);

    Optional<ClientFeed> findByTitle(String title);

    @Query("SELECT f from ClientFeed f order by f.pricePerDay desc ")
    List<ClientFeed> findAllOrderByPrice();

    @Query("SELECT f from ClientFeed f order by  f.createdAt")
    List<ClientFeed> findAllOrderByCreatedAt();

    @Query("SELECT f from ClientFeed f where f.status = :status order by  f.createdAt")
    List<ClientFeed> getFeedOrderByStatus(@Param("status") ItemStatus status);

    @Query("SELECT f from ClientFeed f where f.category = :categoryId ")
    List<ClientFeed> findAllByCategory(@Param("categoryId") Long categoryId);
}
