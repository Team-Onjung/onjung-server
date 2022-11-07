package com.onjung.onjung.dto;

import com.onjung.onjung.domain.Room;
import com.onjung.onjung.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatMessage {

    User sender;

    Long roomId;

    String message;

    LocalDateTime sendDate;

    @Builder
    public ChatMessage(
            User sender,
            Long roomId,
            String message,
            LocalDateTime sendDate
    ){
        this.sender = sender;
        this.roomId = roomId;
        this.message = message;
        this.sendDate = sendDate;
    }

}
