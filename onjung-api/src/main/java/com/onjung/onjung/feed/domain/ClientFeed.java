package com.onjung.onjung.feed.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onjung.onjung.feed.domain.status.ItemStatus;
import com.onjung.onjung.user.domain.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Entity
@Getter @Setter
@Table(name = "ClientFeed")
@DynamicInsert
@NoArgsConstructor
public class ClientFeed extends Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ColumnDefault("0")
    @Column(name="feedback_cnt", nullable = false)
    private int feedbackCnt;

    @ColumnDefault("0")
    private Long pricePerDay;

    @NotNull
    private String image;

    @PrePersist
    public void setDefault(){
        this.status = this.status == null ? ItemStatus.STATUS_POSSIBLE : this.status;
    }

    @Builder
    public ClientFeed(
            Category category,
            User writer,
            String title,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Long duration,
            String content,
            String image,
            Long pricePerDay
    )
    {
        this.writer = writer;
        this.title = title;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.content = content;
        this.image = image;
        this.pricePerDay = pricePerDay;
    }

    public void addFeedbackCnt(){
        this.feedbackCnt += 1;
    }

    public void changeStatus(ItemStatus status){
        this.status=status;
    }
}
