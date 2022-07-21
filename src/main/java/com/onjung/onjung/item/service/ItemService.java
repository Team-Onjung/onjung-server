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

    public void saveItem(ItemDto itemDto){
        Item item= Item.builder()
                .name(itemDto.getName())
                .deposit(itemDto.getDeposit())
                .startDate(itemDto.getStartDate())
                .endDate(itemDto.getEndDate())
                .duration(itemDto.getDuration())
                .rentalFee(itemDto.getRentalFee())
                .category(itemDto.getCategory())
                .build();
    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Optional<Item> findOne(String itemName){
        return itemRepository.findByName(itemName);
    }


}
