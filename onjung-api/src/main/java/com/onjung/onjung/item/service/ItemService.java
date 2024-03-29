package com.onjung.onjung.item.service;

import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.feed.domain.Category;
import com.onjung.onjung.item.domain.Item;
import com.onjung.onjung.item.dto.ItemDto;
import com.onjung.onjung.item.repository.CategoryRepository;
import com.onjung.onjung.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {


//    하나 읽기 / 여러개 읽기 / 생성하기 / 업데이트하기 / 지우기
    private final ItemRepository itemRepository;
    
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    @Cacheable(value="itemCaching")
    public List<Item> readAllItems(){
        return itemRepository.findAll();
    }

    @Transactional
    @CacheEvict(value="itemCaching", allEntries = true)
    public void createItem(ItemDto itemDto) {

        try{
            Category requestCategory = categoryRepository.findById(itemDto.getCategoryId()).get();

            Item item = Item.builder()
                    .name(itemDto.getName())
                    .deposit(itemDto.getRentalFee())
                    .category(requestCategory)
                    .duration(itemDto.getDuration())
                    .startDate(itemDto.getStartDate())
                    .endDate(itemDto.getEndDate())
                    .rentalFee(itemDto.getRentalFee())
                    .build();

            itemRepository.save(item);
        }

        catch(NoSuchElementException e){
            throw new DataNotFoundException();
        }

    }

    @Transactional(readOnly = true)
    @Cacheable(value = "itemCaching", key = "#itemId")
    public Optional<Item> readItem(Long itemId) {
        Optional<Item> item = itemRepository.findById(itemId);
        if (item.isPresent()){
            return item;
        }else {
            throw new DataNotFoundException();
        }
    }

    @Transactional
    @CachePut(value = "itemCaching", key = "#itemId")
    public Item putItem(Long itemId, ItemDto itemDto) {
        try{
            Item item = itemRepository.findById(itemId).get();

            Category requestCategory = categoryRepository.findById(itemDto.getCategoryId()).get();

//            item.setCategory(requestCategory);
            item.setDeposit(itemDto.getDeposit());
            item.setDuration(itemDto.getDuration());
            item.setStartDate(item.getStartDate());
            item.setEndDate(itemDto.getEndDate());
            item.setName(itemDto.getName());
            item.setRentalFee(itemDto.getRentalFee());

            itemRepository.save(item);

            return item;

        }catch(NoSuchElementException e){
            throw new DataNotFoundException();
        }
    }

    @CacheEvict(value = "itemCaching", allEntries = true)
    public void deleteItem(Long itemId){
        Optional<Item> item = itemRepository.findById(itemId);
        if(item.isPresent()){
            itemRepository.delete(item.get());
        }else{
            throw new DataNotFoundException();
        }
    }

}
