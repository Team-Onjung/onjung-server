package com.onjung.onjung.feed.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onjung.onjung.feed.domain.status.ItemStatus;
import com.onjung.onjung.user.domain.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class Feed {
    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY, optional=false)
    @JoinColumn(name = "writer_id")
    protected User writer;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "created_at")
    protected LocalDateTime createdAt;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name="CATEGORY_ID")
    protected Category category;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="start_date", nullable = false)
    protected LocalDateTime startDate;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="end_date", nullable = false)
    protected LocalDateTime endDate;

    @NotNull
    protected Long duration;

    @Column(length = 32, nullable = false)
    protected String title;

    @NotNull
    protected String content;

    //    수령 안됨(가능) / 수령 대기(예약) / 수령 중(배송 중)/ 수령 취소/ 수령 완료
    @NotNull
    @Enumerated(EnumType.STRING)
    protected ItemStatus status;

    @ColumnDefault("0")
    @Column(name="access_cnt", nullable = false)
    private Long accessCnt;

    abstract void addFeedbackCnt();

    public abstract void changeStatus(ItemStatus status);

    public void addAccessCnt(){
        this.accessCnt += 1;
    }

    public boolean isPossible() {
        return getStatus() == ItemStatus.STATUS_POSSIBLE;
    }
}
