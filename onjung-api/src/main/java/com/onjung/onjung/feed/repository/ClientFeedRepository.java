package com.onjung.onjung.feed.repository;

import com.onjung.onjung.feed.domain.ClientFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientFeedRepository extends JpaRepository<ClientFeed,Long> {

    Optional<ClientFeed> findById(Long id);

    @Query(value = "SELECT * FROM client_feed " +
            "WHERE client_feed.category_id like %:category% AND client_feed.status like %:status% ;", nativeQuery = true)
    Optional<List<ClientFeed>> findByFilteringCondition(@Param("category") String category,
                                                        @Param("status") String status

                                                  );
}
