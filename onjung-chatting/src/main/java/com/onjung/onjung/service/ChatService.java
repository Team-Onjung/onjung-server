package com.onjung.onjung.service;

import com.onjung.onjung.domain.Room;
import com.onjung.onjung.dto.RoomDto;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.repository.ClientFeedRepository;
import com.onjung.onjung.feed.repository.ServerFeedRepository;
import com.onjung.onjung.repository.ChatRepository;
import com.onjung.onjung.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final RoomRepository roomRepository;
    private final ServerFeedRepository serverFeedRepository;
    private final ClientFeedRepository clientFeedRepository;

    public Room createRoom(RoomDto roomDto){
        String feedType =  roomDto.getFeedType();

        if (feedType == "server"){
            ServerFeed serverFeed = serverFeedRepository.findById(roomDto.getFeedId()).get();
            return Room.builder()
                    .serverFeed(serverFeed)
                    .starter(roomDto.getStarter())
                    .build();

        }else{
            ClientFeed clientFeed = clientFeedRepository.findById(roomDto.getFeedId()).get();
            return Room.builder()
                    .clientFeed(clientFeed)
                    .starter(roomDto.getStarter())
                    .build();
        }
    }
}
