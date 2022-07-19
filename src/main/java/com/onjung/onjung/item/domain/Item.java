package com.onjung.onjung.item.domain;

import com.onjung.onjung.category.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name="item")
@DynamicInsert
@NoArgsConstructor
public class Item {

    @Id
    @GeneratedValue
    @Column(name="ITEM_ID")
    private Long id;

    @Column(length=30, nullable = false)
    private String name;

    @NotNull
    private int deposit;

    @Column(name="start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name="end_date", nullable = false)
    private LocalDateTime endDate;

    @NotNull
    private LocalDateTime duration;

    @Column(name="rental_fee", nullable = false)
    private int rentalFee;

    @ManyToOne
    @JoinColumn(name="CATEGORY_ID")
    private Category category;

    @Builder
    public Item(
            String name,
            int deposit,
            LocalDateTime startDate,
            LocalDateTime endDate,
            LocalDateTime duration,
            int rentalFee,
            Category category

            ){
        this.id = id;
        this.name = name;
        this.deposit = deposit;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.rentalFee = rentalFee;
        this.category = category;
    }
}


