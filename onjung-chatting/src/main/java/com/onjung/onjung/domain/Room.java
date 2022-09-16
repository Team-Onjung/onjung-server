package com.onjung.onjung.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY, optional=false)
    @JoinColumn(name = "serverfeed_id")
    private ServerFeed serverFeed;

    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY, optional=false)
    @JoinColumn(name = "clientfeed_id")
    private ClientFeed clientFeed;

    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY, optional=false)
    @JoinColumn(name = "starter_id")
    private User starter;

//      필요할까? 해당 room 에 해당하는 chat 을 불러오는게 아니라 subscriber 한테 전송하는건데..
//    @JsonIgnore
//    @OneToMany(mappedBy="room")
//    private List<Chat> ChatList = new ArrayList<>();

    @Builder
    public Room(
        ServerFeed serverFeed,
        ClientFeed clientFeed,
        User starter
    ){
        this.serverFeed = serverFeed;
        this.clientFeed = clientFeed;
        this.starter = starter;
    }

    public static Room createRoom(
            ServerFeed serverFeed,
            ClientFeed clientFeed,
            User starter
    ){
        return Room.builder()
                .clientFeed(clientFeed)
                .serverFeed(serverFeed)
                .starter(starter)
                .build();
    }

}
