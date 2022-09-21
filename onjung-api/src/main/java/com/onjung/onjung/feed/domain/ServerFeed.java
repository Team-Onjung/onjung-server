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
@Getter
@Setter
@Table(name = "ServerFeed")
@DynamicInsert
@NoArgsConstructor
public class ServerFeed implements Feed{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne
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

    @Column(length = 32, nullable = false)
    private String title;

    @NotNull
    @Column(name="start_date", nullable = false)
    private LocalDateTime startDate;

    @NotNull
    @Column(name="end_date", nullable = false)
    private LocalDateTime endDate;

    @NotNull
    private LocalDateTime duration;

    @NotNull
    private String content;

    @NotNull
    private int deposit;

    @NotNull
    @Column(name="rental_fee", nullable = false)
    private int rentalFee;

    @NotNull
    @ColumnDefault("0")
    @Column(name="feedback_cnt", nullable = false)
    private int feedbackCnt;

    @NotNull
    @ColumnDefault("0")
    @Column(name="commission_cnt", nullable = false)
    private double commissionFee;

    //    방문자 수
//    @Column(columnDefinition = "bigint default 0")
//    private long visitedCnt;

    //    수령 안됨(가능) / 수령 대기(예약) / 수령 중(배송 중)/ 수령 취소/ 수령 완료
    @NotNull
    @Enumerated(EnumType.STRING)
    private ItemStatus status;

    private String image;

    @PrePersist
    public void setDefault(){
        this.status = this.status == null ? ItemStatus.STATUS_POSSIBLE : this.status;
    }

    @Builder
    public ServerFeed(
            Category category,
            User writer,
            String title,
            String content,
            LocalDateTime startDate,
            LocalDateTime endDate,
            LocalDateTime duration,
            String image,
            int rentalFee,
            int deposit,
            double commissionFee
            ) {
        this.writer = writer;
        this.category = category;
        this.content = content;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.image = image;
        this.rentalFee = rentalFee;
        this.deposit = deposit;
        this.commissionFee = commissionFee;
    }

    public void addFeedbackCnt(){
        this.feedbackCnt+=1;
    }

    public void changeStatus(ItemStatus status){
        this.status=status;
    }
}
