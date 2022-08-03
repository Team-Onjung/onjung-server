package com.onjung.onjung.item.domain;


import com.onjung.onjung.item.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="Category")
@DynamicInsert
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CATEGORY_ID")
    private Long id;

    @NotNull
    private String name;

    @OneToMany(mappedBy="category")
    private List<Item> items = new ArrayList<>();

    @Builder
    public Category(
            String name
    ){
        this.name = name;
    }

}