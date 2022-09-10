package com.onjung.onjung.feed.domain;

import com.onjung.onjung.user.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "UserRentalFeed")
@DynamicInsert
@NoArgsConstructor
public class UserRentalFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String feedCategory;

    
    private User clientUserID;

    private User serverUserID;

    private Long feedId;
}
