package com.onjung.onjung.item.dto;

import com.onjung.onjung.category.domain.Category;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
public class ItemDto {

    @NotBlank
    String name;

    int deposit;

    LocalDateTime startDate;

    LocalDateTime endDate;

    LocalDateTime duration;

    int rentalFee;

    Category category;

    @Builder
    public ItemDto(
            String name,
            int deposit,
            LocalDateTime startDate,
            LocalDateTime endDate,
            LocalDateTime duration,
            int rentalFee,
            Category category
    ){
      this.name = name;
      this.deposit = deposit;
      this.startDate = startDate;
      this.endDate = endDate;
      this.duration = duration;
      this.rentalFee = rentalFee;
      this.category = category;
    }
}
