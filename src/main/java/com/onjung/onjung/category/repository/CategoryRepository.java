package com.onjung.onjung.category.repository;

import com.onjung.onjung.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<Category> findById(Long CategoryId);

}
