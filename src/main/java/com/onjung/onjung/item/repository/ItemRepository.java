package com.onjung.onjung.item.repository;

import com.onjung.onjung.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long> {
    List<Item> findByName(String itemName);

    Optional<Item> findById(Long itemId);
}
