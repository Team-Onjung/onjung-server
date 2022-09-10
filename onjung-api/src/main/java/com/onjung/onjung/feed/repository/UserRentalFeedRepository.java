package com.onjung.onjung.feed.repository;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.domain.UserRentalFeed;
import com.onjung.onjung.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRentalFeedRepository extends JpaRepository<UserRentalFeed,Long> {

//    유저와 관련된 모든 장부를 조회
    @Query("select snapshot from UserRentalFeed snapshot where snapshot.clientUserID = :user or snapshot.serverUserID = :user")
    List<Optional<UserRentalFeed>> findSnapshots(@Param("user") User user);
}
