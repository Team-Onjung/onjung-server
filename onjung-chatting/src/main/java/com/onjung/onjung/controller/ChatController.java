package com.onjung.onjung.controller;

import com.onjung.onjung.domain.Chat;
import com.onjung.onjung.domain.Room;
import com.onjung.onjung.dto.ChatMessage;
import com.onjung.onjung.dto.RoomDto;
import com.onjung.onjung.repository.RoomService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;


public class ChatController {

    private RoomService roomService;

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

    public Room createRoom(RoomDto roomDto){

        String feedType =  roomDto.getFeedType();

        if (feedType == "server"){
            ServerFeed serverFeed = serverFeedRepository.findById(roomDto.getFeedId());
            return Room.builder()
                    .serverFeed(serverFeed)
                    .starter(roomDto.getStarter())
                    .build();

        }else{
            ClientFeed clientFeed = clientFeedRepository.findById(roomDto.getFeedId());
            return Room.builder()
                    .clientFeed(clientFeed)
                    .starter(roomDto.getStarter())
                    .build();
        }

    }

}
