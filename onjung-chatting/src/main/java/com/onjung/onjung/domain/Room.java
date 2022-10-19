package com.onjung.onjung.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
//import com.onjung.onjung.user.domain.User;
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
    @JoinColumn(name = "sender_id")
    private User sender;

    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY, optional=false)
    @JoinColumn(name = "receiver_id")
    private User receiver;

    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY, optional=false)
    @JoinColumn(name = "starter_id")
    private User starter;

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
