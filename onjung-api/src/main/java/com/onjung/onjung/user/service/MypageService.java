package com.onjung.onjung.user.service;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.domain.UserRentalFeed;
import com.onjung.onjung.feed.domain.staus.FeedStatus;
import com.onjung.onjung.feed.repository.ClientFeedRepository;
import com.onjung.onjung.feed.repository.ServerFeedRepository;
import com.onjung.onjung.feed.repository.UserRentalFeedRepository;
import com.onjung.onjung.user.domain.User;

import com.onjung.onjung.user.dto.MypageResponseDto;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MypageService {

    private final UserRepository userRepository;
    private final ClientFeedRepository clientFeedRepository;
    private final ServerFeedRepository serverFeedRepository;
    private final UserRentalFeedRepository userRentalFeedRepository;

    public MypageResponseDto mypageData(){
        //유저를 받아왔다고 가정-> 이후 교체 필요
        User testUser=userRepository.findById(1L).get();
        User user=testUser;

        MypageResponseDto responseDto=new MypageResponseDto();

        //내가 빌려준 피드
        ArrayList<ServerFeed> myLentFeeds=new ArrayList<ServerFeed>();
        //내가 빌린 피드
        ArrayList<ServerFeed> myBorrowedFeeds=new ArrayList<ServerFeed>();
        //내가 요청한 피드
        ArrayList<ClientFeed> myRequesteddFeeds=new ArrayList<ClientFeed>();

        List<Optional<UserRentalFeed>> snapshots=userRentalFeedRepository.findSnapshots(user);
        for (Optional<UserRentalFeed> snapshot : snapshots) {
            if (snapshot.isPresent()){
                UserRentalFeed s=snapshot.get();
                if(s.getFeedCategory().equals(FeedStatus.SERVER_FEED) && s.getServerUserID().equals(user)){
                    myLentFeeds.add(serverFeedRepository.findById(s.getFeedId()).get());
                } else if (s.getFeedCategory().equals(FeedStatus.SERVER_FEED) && s.getClientUserID().equals(user)) {
                    myBorrowedFeeds.add(serverFeedRepository.findById(s.getFeedId()).get());
                } else if (s.getFeedCategory().equals(FeedStatus.CLIENT_FEED) && s.getClientUserID().equals(user)){
                    myRequesteddFeeds.add(clientFeedRepository.findById(s.getFeedId()).get());
                }
            }
        }

        responseDto.setUsername(user.getUsername());
        responseDto.setMyLentFeeds(myLentFeeds);
        responseDto.setMyBorrowedFeeds(myBorrowedFeeds);
        responseDto.setMyRequesteddFeeds(myRequesteddFeeds);

        return responseDto;
    }
}
