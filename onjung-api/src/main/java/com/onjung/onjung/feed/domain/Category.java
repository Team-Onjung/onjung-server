package com.onjung.onjung.feed.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysql.cj.xdevapi.Client;
import com.onjung.onjung.item.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="Category")
@DynamicInsert
@NoArgsConstructor
public class Category implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="CATEGORY_ID")
    private Long id;

    @NotNull
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy="category")
    private List<ServerFeed> serverFeedList = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy="category")
    private List<ClientFeed> clientFeedList = new ArrayList<>();

    @Builder
    public Category(
            String name
    ){
        this.name = name;
    }

}
