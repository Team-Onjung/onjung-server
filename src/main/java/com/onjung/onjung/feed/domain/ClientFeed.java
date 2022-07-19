package com.onjung.onjung.feed.domain;

import com.onjung.onjung.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User writer;

    //이후 아이템 완성시 수정필요
    @NotNull
    private String itemId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(length = 32, nullable = false)
    private String title;

    @NotNull
    private String body;

    //    방문자 수
    @Column(columnDefinition = "bigint default 0")
    private long visitedCnt;

    //    수령 안됨(가능) / 수령 대기(예약) / 수령 중(배송 중)/ 수령 취소/ 수령 완료
    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @Builder
    public ClientFeed(
                      User writer,
                      String title,
                      String body,
                      String itemId,
                      Status status) {
        this.writer = writer;
        this.itemId = itemId;
        this.title = title;
        this.body = body;
        this.status = status;
    }

    public void addCnt(){
        this.visitedCnt+=1;
    }

    public void changeStatus(Status status){
        this.status=status;
    }
}
