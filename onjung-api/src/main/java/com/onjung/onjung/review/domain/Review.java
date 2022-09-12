package com.onjung.onjung.review.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onjung.onjung.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name="Review")
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name = "sender_id")
    private User sender;

    private String content;

    @ColumnDefault("0")
    private float rate;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Review(
            User receiver,
            User sender,
            String content,
            float rate
    ){
        this.receiver = receiver;
        this.sender = sender;
        this.content = content;
        this.rate = rate;
    }

}
