package com.onjung.onjung.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mysql.cj.protocol.ColumnDefinition;
import com.onjung.onjung.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_id", nullable = false)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY, optional=false)
    @JoinColumn(name = "sender_id")
    private User sender;

    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY, optional=false)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(columnDefinition = "TEXT")
    private String message;

    @CreatedDate
    @Column(updatable = false, name="send_date")
    private LocalDateTime sendDate;

    public void setId(Long id) {
        this.id = id;
    }

    @Builder
    public Chat(
            Room room,
            User sender,
            String message
    ){
        this.room = room;
        this.sender = sender;
        this.message = message;
        this.sendDate = LocalDateTime.now();
    }

    public static Chat createChat(
            Room room,
            User sender,
            String message
    ){
        return Chat.builder()
                .room(room)
                .sender(sender)
                .message(message)
                .build();
    }

}
