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

}
