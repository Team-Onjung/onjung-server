package com.onjung.onjung.item.repository;

import com.onjung.onjung.feed.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
