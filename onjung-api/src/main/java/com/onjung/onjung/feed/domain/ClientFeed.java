package com.onjung.onjung.feed.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onjung.onjung.item.domain.Item;
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
@Getter
@Setter
@Table(name = "ClientFeed")
@DynamicInsert
@NoArgsConstructor
public class ClientFeed implements Feed{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY, optional=false)
    @JoinColumn(name = "writer_id")
    private User writer;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="CATEGORY_ID")
    private Category category;

    @NotNull
    @Column(name="start_date", nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @Column(name="end_date", nullable = false)
    private LocalDateTime endDate;

    @NotNull
    private Long duration;

    @Column(length = 32, nullable = false)
    private String title;

    @NotNull
    private String content;

    @NotNull
    @ColumnDefault("0")
    @Column(name="feedback_cnt", nullable = false)
    private int feedbackCnt;

    //    수령 안됨(가능) / 수령 대기(예약) / 수령 중(배송 중)/ 수령 취소/ 수령 완료
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @ColumnDefault("0")
    private Long pricePerDay;

    @NotNull
    private String image;

    @PrePersist
    public void setDefault(){
        this.status = this.status == null ? Status.STATUS_POSSIBLE : this.status;
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

        this.feedbackCnt+=1;
    }

    public void changeStatus(Status status){
        this.status=status;
    }
}
