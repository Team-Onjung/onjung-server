package com.onjung.onjung.feed.service;

import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import com.onjung.onjung.feed.repository.ServerFeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServerFeedService implements FeedService{

    private final ServerFeedRepository serverFeedRepository;

    public List<ServerFeed> readAllFeed(){
        return serverFeedRepository.findAll();
    }

    public Optional<ServerFeed> readFeed(Long feedId){

        return serverFeedRepository.findById(feedId);
    }

    @Transactional
    public void createFeed(FeedRequestDto feedRequestDto){
        try {
            ServerFeed feed = ServerFeed.builder()
                    .writer(feedRequestDto.getWriter())
                    .title(feedRequestDto.getTitle())
                    .body(feedRequestDto.getBody())
                    .itemId(feedRequestDto.getItemId())
                    .build();

            serverFeedRepository.save(feed);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void patchFeed(Long feedId, FeedRequestDto requestDto){
        final Optional<ServerFeed> serverFeed= serverFeedRepository.findById(feedId);
        if(serverFeed.isPresent()){
            if(requestDto.getWriter()!=null){
                serverFeed.get().setWriter(requestDto.getWriter());
            }
            if(requestDto.getTitle()!=null){
                serverFeed.get().setTitle(requestDto.getTitle());
            }
            if(requestDto.getBody()!=null){
                serverFeed.get().setBody(requestDto.getBody());
            }
            if(requestDto.getItemId()!=null){
                serverFeed.get().setItemId(requestDto.getItemId());
            }
        }
        serverFeedRepository.save(serverFeed.get());
        return;
    }

    public void deleteFeed(Long feedId){
        Optional<ServerFeed> serverFeed=serverFeedRepository.findById(feedId);
        if(serverFeed.isPresent()){
            serverFeedRepository.delete(serverFeed.get());
        }
    }
}
