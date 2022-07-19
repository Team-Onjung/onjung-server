package com.onjung.onjung.feed.domain;

import com.onjung.onjung.feed.repository.ClientFeedRepository;
import com.onjung.onjung.user.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClientFeedTest {

    @Test
    void build() {
        User testUser=new User();

        ClientFeed clientFeed= ClientFeed.builder()
                .title("테스트 코드입니다.")
                .body("테스트 코드입니다.")
                .writer(testUser)
                .itemId("테스트 코드입니다.")
                .status(Status.STATUS_POSSIBLE)
                .build();

        Assertions.assertEquals(clientFeed.getTitle(),"테스트 코드입니다.");
        Assertions.assertEquals(clientFeed.getBody(),"테스트 코드입니다.");
        Assertions.assertEquals(clientFeed.getWriter(),testUser);
        Assertions.assertEquals(clientFeed.getItemId(),"테스트 코드입니다.");
        Assertions.assertEquals(clientFeed.getStatus(),Status.STATUS_POSSIBLE);
    }

    @Test
    void addCnt() {
        ClientFeed clientFeed=new ClientFeed();
        Assertions.assertEquals(clientFeed.getVisitedCnt(),0);

        clientFeed.addCnt();
        Assertions.assertEquals(clientFeed.getVisitedCnt(),1);
    }

    @Test
    void changeStatus() {
        ClientFeed clientFeed=new ClientFeed();
        Assertions.assertEquals(clientFeed.getStatus(),Status.STATUS_POSSIBLE);

        clientFeed.changeStatus(Status.STATUS_CANCELED);
        Assertions.assertEquals(clientFeed.getStatus(),Status.STATUS_CANCELED);
    }
}
