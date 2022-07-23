package com.onjung.onjung.item.dto;

import com.onjung.onjung.item.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;


@Getter
@Setter
public class ItemDto {

    @NotBlank
    private String name;

    private int deposit;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private LocalDateTime duration;

    private int rentalFee;

    private Category category;

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
