package com.onjung.onjung.item.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onjung.onjung.feed.domain.Category;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="item")
@DynamicInsert
@NoArgsConstructor
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ITEM_ID")
    private Long id;

    @NotNull
    @Column(length=30, nullable = false)
    private String name;

    @NotNull
    private int deposit;

    @NotNull
    @Column(name="start_date", nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @Column(name="end_date", nullable = false)
    private LocalDateTime endDate;

    @NotNull
    private LocalDateTime duration;

    @NotNull
    @Column(name="rental_fee", nullable = false)
    private int rentalFee;
    
    @ManyToOne
    @JoinColumn(name="CATEGORY_ID")
    private Category category;

//    @JsonIgnore
//    @OneToOne(mappedBy = "item")
//    private ClientFeed clientFeed;

//    @JsonIgnore
//    @OneToOne(mappedBy = "item")
//    private ServerFeed serverFeed;

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


