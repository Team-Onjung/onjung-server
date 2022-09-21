package com.onjung.onjung.feed.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onjung.onjung.feed.domain.status.FeedStatus;
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

    @Enumerated(EnumType.STRING)
    private FeedStatus feedCategory;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snapshot", insertable = false, updatable = false)
    private User clientUserID;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "snapshot")
    private User serverUserID;

    private Long feedId;
}
