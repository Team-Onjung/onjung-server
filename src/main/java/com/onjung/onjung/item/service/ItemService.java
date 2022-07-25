package com.onjung.onjung.item.service;

import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.exception.InvalidParameterException;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.item.domain.Category;
import com.onjung.onjung.item.domain.Item;
import com.onjung.onjung.item.dto.ItemDto;
import com.onjung.onjung.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {


//    하나 읽기 / 여러개 읽기 / 생성하기 / 업데이트하기 / 지우기
    private final ItemRepository itemRepository;

    @Transactional(readOnly = true)
    @Cacheable(value="itemCaching")
    public List<Item> readAllItems(){
        return itemRepository.findAll();
    }

    @Transactional
    @CacheEvict(value="itemCaching", allEntries = true)
    public void createItem(ItemDto itemDto) throws Exception {

        try{
            Item item = Item.builder()
                    .name(itemDto.getName())
                    .deposit(itemDto.getRentalFee())
                    .category(itemDto.getCategory())
                    .duration(itemDto.getDuration())
                    .startDate(itemDto.getStartDate())
                    .endDate(itemDto.getEndDate())
                    .rentalFee(itemDto.getRentalFee())
                    .build();
            itemRepository.save(item);

        }catch (IllegalArgumentException e){
            throw new InvalidParameterException();
        }
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "itemCaching", key = "#itemId")
    public Optional<Item> readItem(Long itemId) throws InterruptedException {
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isPresent()){
            return item;
        }else {
            throw new DataNotFoundException();
        }
    }

    @Transactional
    @CachePut(value = "itemCaching", key = "#itemId")
    public void putItem(Long itemId, ItemDto itemDto){
        Item item = itemRepository.findById(itemId).get();

        item.setCategory(itemDto.getCategory());
        item.setDeposit(itemDto.getDeposit());
        item.setDuration(itemDto.getDuration());
        item.setStartDate(item.getStartDate());
        item.setEndDate(itemDto.getEndDate());
        item.setName(itemDto.getName());
        item.setRentalFee(itemDto.getRentalFee());

        itemRepository.save(item);

    }

    @CacheEvict(value = "itemCaching", allEntries = true)
    public void deleteItem(Long itemId){
        Optional<Item> item = itemRepository.findById(itemId);
        if(item.isPresent()){
            itemRepository.delete(item.get());
        }
    }


}
