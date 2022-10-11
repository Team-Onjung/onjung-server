package com.onjung.onjung.feed.domain;

import com.onjung.onjung.user.domain.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "ServerFeed")
@DynamicInsert
@NoArgsConstructor
public class ServerFeed extends Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long minimumDuration;

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
    private Long commissionFee;

    //    방문자 수
//    @Column(columnDefinition = "bigint default 0")
//    private long visitedCnt;

    //    수령 안됨(가능) / 수령 대기(예약) / 수령 중(배송 중)/ 수령 취소/ 수령 완료
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    private String image;

    @PrePersist
    public void setDefault(){
        this.status = this.status == null ? Status.STATUS_POSSIBLE : this.status;
    }

    @Builder
    public ServerFeed(
            Category category,
            User writer,
            String title,
            String content,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Long duration,
            Long minimumDuration,
            String image,
            int rentalFee,
            int deposit,
            Long commissionFee
    ) {
        this.writer = writer;
        this.category = category;
        this.content = content;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.minimumDuration = minimumDuration;
        this.image = image;
        this.rentalFee = rentalFee;
        this.deposit = deposit;
        this.commissionFee = commissionFee;
    }

    public void addFeedbackCnt(){
        this.feedbackCnt+=1;
    }

    public void changeStatus(Status status){
        this.status=status;
    }
}
