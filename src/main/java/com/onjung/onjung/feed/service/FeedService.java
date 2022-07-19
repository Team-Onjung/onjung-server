package com.onjung.onjung.feed.service;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import com.onjung.onjung.feed.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;

    @Transactional
    public void saveClientFeed(FeedRequestDto feedRequestDto){
        try {
            ClientFeed feed = ClientFeed.builder()
                            .writer(feedRequestDto.getWriter())
                            .title(feedRequestDto.getTitle())
                            .body(feedRequestDto.getBody())
                            .itemId(feedRequestDto.getItemId())
                            .build();

            feedRepository.save(feed);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Transactional
    public void saveServerFeed(FeedRequestDto feedRequestDto){
        try {
            ServerFeed feed = ServerFeed.builder()
                    .writer(feedRequestDto.getWriter())
                    .title(feedRequestDto.getTitle())
                    .body(feedRequestDto.getBody())
                    .itemId(feedRequestDto.getItemId())
                    .build();

            feedRepository.save(feed);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
