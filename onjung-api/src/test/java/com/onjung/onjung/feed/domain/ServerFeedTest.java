package com.onjung.onjung.feed.domain;

import com.onjung.onjung.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerFeedTest {

    @Test
    void build() {
        User testUser=new User();

        ServerFeed serverFeed= ServerFeed.builder()
                .title("테스트 코드입니다.")
                .body("테스트 코드입니다.")
                .writer(testUser)
                .itemId("테스트 코드입니다.")
                .build();

        Assertions.assertEquals(serverFeed.getTitle(),"테스트 코드입니다.");
        Assertions.assertEquals(serverFeed.getBody(),"테스트 코드입니다.");
        Assertions.assertEquals(serverFeed.getWriter(),testUser);
        Assertions.assertEquals(serverFeed.getItemId(),"테스트 코드입니다.");
    }

    @Test
    void addCnt() {
        ServerFeed serverFeed=new ServerFeed();
        Assertions.assertEquals(serverFeed.getVisitedCnt(),0);

        serverFeed.addCnt();
        Assertions.assertEquals(serverFeed.getVisitedCnt(),1);
    }

    @Test
    void changeStatus() {
        ServerFeed serverFeed=new ServerFeed();

        serverFeed.changeStatus(Status.STATUS_CANCELED);
        Assertions.assertEquals(serverFeed.getStatus(),Status.STATUS_CANCELED);

        serverFeed.changeStatus(Status.STATUS_POSSIBLE);
        Assertions.assertEquals(serverFeed.getStatus(),Status.STATUS_POSSIBLE);
    }

}