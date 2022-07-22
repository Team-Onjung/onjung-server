package com.onjung.onjung.category.dto;


import com.onjung.onjung.item.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter

public class CategoryDto {

    @NotBlank
    private String name;

    private List<Item> items;

    @Builder
    public CategoryDto(
            String name,
            List<Item> items
    ){
        this.name = name;
    }

}
