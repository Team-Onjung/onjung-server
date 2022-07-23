package com.onjung.onjung.item.domain;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public class ItemTest {

    @Test
    @Transactional
    void ItemEntityTest(){

        Category category = Category.builder()
                .name("문구류")
                .build();

        LocalDateTime startDate = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        LocalDateTime duration = LocalDateTime.of(1, 1, 1, 0, 1, 0);

        Item testItem = Item.builder()
                .category(category)
                .deposit(1)
                .duration(duration)
                .name("테스트")
                .rentalFee(1)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        Assertions.assertEquals(testItem.getCategory(),category);
        Assertions.assertEquals(testItem.getDeposit(),1);
        Assertions.assertEquals(testItem.getDuration(),duration);
        Assertions.assertEquals(testItem.getName(),"테스트");
        Assertions.assertEquals(testItem.getRentalFee(),1);
        Assertions.assertEquals(testItem.getStartDate(),startDate);
        Assertions.assertEquals(testItem.getEndDate(),endDate);
    }
}
