package com.onjung.onjung.controller;

import com.onjung.onjung.domain.Chat;
import com.onjung.onjung.domain.Room;
import com.onjung.onjung.dto.ChatMessage;
import com.onjung.onjung.dto.RoomDto;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.repository.ClientFeedRepository;
import com.onjung.onjung.feed.repository.ServerFeedRepository;
import com.onjung.onjung.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;


public class ChatController {

    private RoomRepository roomService;

    private ServerFeedRepository serverFeedRepository;

    private ClientFeedRepository clientFeedRepository;

    @MessageMapping("/{roomId}") // 여기로 오면 해당 메서드 호출
    @SendTo("/room/{roomId}") // 결과를 해당 엔드포인트로 전송
    public Chat createChat(@DestinationVariable Long roomId, ChatMessage chatMessage){
        Room room = roomService.findById(roomId).get();
        return Chat.builder()
                .room(room)
                .message(chatMessage.getMessage())
                .sender(chatMessage.getSender())
                .build();
    }

    public ResponseEntity createRoom(RoomDto roomDto){
        createRoom(roomDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("채팅방이 생성되었습니다.");
    }

}
