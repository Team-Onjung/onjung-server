package com.onjung.onjung.user.dto;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
public class MypageResponseDto implements Serializable {

//    닉네임 예: 얼리어답터 지우
    String username;

//    나의 상품
    ArrayList<ServerFeed> myLentFeeds;

//    대여한 상품
    ArrayList<ServerFeed> myBorrowedFeeds;

//    대여 요청
    ArrayList<ClientFeed> myRequesteddFeeds;

//    나의 정
//    String myAffection; -> 이후 추가 예정

//    찜 목록 -> 이후 추가 예정

//    거래후기 -> 이후 추가 예정

}
