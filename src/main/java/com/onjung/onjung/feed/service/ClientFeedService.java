package com.onjung.onjung.feed.service;

import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import com.onjung.onjung.feed.repository.ClientFeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientFeedService implements FeedService{

    private final ClientFeedRepository clientFeedRepository;

    @Transactional
    public void createFeed(FeedRequestDto feedRequestDto){
        try {
            ClientFeed feed = ClientFeed.builder()
                    .writer(feedRequestDto.getWriter())
                    .title(feedRequestDto.getTitle())
                    .body(feedRequestDto.getBody())
                    .itemId(feedRequestDto.getItemId())
                    .build();

            clientFeedRepository.save(feed);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public List<ClientFeed> readAllFeed(){
        return clientFeedRepository.findAll();
    }

    public Optional<ClientFeed> readFeed(Long feedId){
        return clientFeedRepository.findById(feedId);
    }

    @Transactional
    public void patchFeed(Long feedId, FeedRequestDto requestDto){
        final Optional<ClientFeed> clientFeed= clientFeedRepository.findById(feedId);
        if(clientFeed.isPresent()){
            if(requestDto.getWriter()!=null){
                clientFeed.get().setWriter(requestDto.getWriter());
            }
            if(requestDto.getTitle()!=null){
                clientFeed.get().setTitle(requestDto.getTitle());
            }
            if(requestDto.getBody()!=null){
                clientFeed.get().setBody(requestDto.getBody());
            }
            if(requestDto.getItemId()!=null){
                clientFeed.get().setItemId(requestDto.getItemId());
            }
        }
        clientFeedRepository.save(clientFeed.get());
        return;
    }

    public void deleteFeed(Long feedId){
        Optional<ClientFeed> clientFeed=clientFeedRepository.findById(feedId);
        if(clientFeed.isPresent()){
            clientFeedRepository.delete(clientFeed.get());
        }
    }
}
