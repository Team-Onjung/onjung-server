package com.onjung.onjung.item.service;

import com.onjung.onjung.exception.DuplicatedUserException;
import com.onjung.onjung.item.domain.Item;
import com.onjung.onjung.item.dto.ItemDto;
import com.onjung.onjung.item.repository.ItemRepository;
import com.onjung.onjung.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public List<Item> findItemsByName(String itemName){
        return itemRepository.findAllByName(itemName);
    }

    public Optional<Item> findOne(Long itemId){
        return itemRepository.findById(itemId);
    }



}
