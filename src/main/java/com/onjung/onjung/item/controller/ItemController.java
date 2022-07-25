package com.onjung.onjung.item.controller;

import com.onjung.onjung.item.domain.Item;
import com.onjung.onjung.item.dto.ItemDto;
import com.onjung.onjung.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/item")
public class ItemController {

    // 하나, 여러개, 업데이트, 삭제, 생성

    private final ItemService itemService;

    @GetMapping()
    public ResponseEntity readAllItems(){
        List<Item> items =  itemService.readAllItems();
        return ResponseEntity.status(HttpStatus.OK).body(items);
    }

    @GetMapping("{itemId}")
    public ResponseEntity readItem(@PathVariable("itemId") Long itemId){
        try{
            Optional<Item> item = itemService.readItem(itemId);
            return ResponseEntity.status(HttpStatus.OK).body(item);
        }catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exception raised in ItemController/readItem");
        }
    }

    @PostMapping()
    public ResponseEntity createItem(@Valid @RequestBody ItemDto itemDto){
        try{
            itemService.createItem(itemDto);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exception raised in ItemController/createItem");
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }


    @PutMapping("{itemId}")
    public ResponseEntity updateItem(@PathVariable("itemId") Long itemId, @Valid @RequestBody ItemDto itemDto){
        try{
            itemService.putItem(itemId, itemDto);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exception raised in ClientFeedController/updateFeed");
        }
        return ResponseEntity.status(HttpStatus.OK).body("ok");
    }

    @DeleteMapping("{itemId}")
    public void deleteItem(@PathVariable("itemId") Long itemId){
        itemService.deleteItem(itemId);
    }

}
