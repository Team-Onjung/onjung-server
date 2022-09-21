package com.onjung.onjung.feed.domain;

import com.onjung.onjung.feed.domain.status.ItemStatus;

public interface Feed {

    void addFeedbackCnt();

    void changeStatus(ItemStatus status);

}
