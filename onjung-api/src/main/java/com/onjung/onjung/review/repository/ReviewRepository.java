package com.onjung.onjung.review.repository;

import com.onjung.onjung.review.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review,Long> {

}
