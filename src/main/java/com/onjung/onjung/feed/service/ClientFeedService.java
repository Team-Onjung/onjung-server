package com.onjung.onjung.feed.service;

import com.onjung.onjung.exception.DataNotFoundException;
import com.onjung.onjung.exception.InvalidParameterException;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.dto.FeedRequestDto;
import com.onjung.onjung.feed.repository.ClientFeedRepository;
import com.onjung.onjung.user.domain.User;
import com.onjung.onjung.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientFeedService implements FeedService{

    private final ClientFeedRepository clientFeedRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createFeed(FeedRequestDto feedRequestDto) throws Exception {
        //임시로 유저 객체 저장, 이후 회원가입한 회원에 한해 저장하는걸로 수정.
        LocalDate birthDate= LocalDate.ofYearDay(2022,1);
        String name=Double.toString(Math.random());

        User testUser=User.builder()
                .email("email")
                .birth(birthDate)
                .locationId("locationId")
                .phone("phone")
                .profileImg("profileImg")
                .profileIntro("profileIntro")
                .provider("provider")
                .university("university")
                .username(name)
                .uuid("uuid")
                .build();
        userRepository.save(testUser);

        Optional<User> savedUser= userRepository.findByUsername(name);

        try {
            ClientFeed feed = ClientFeed.builder()
                    .writer(savedUser.get())
                    .title(feedRequestDto.getTitle())
                    .body(feedRequestDto.getBody())
                    .itemId(feedRequestDto.getItemId())
                    .build();

            clientFeedRepository.save(feed);
        }catch (IllegalArgumentException e){
            throw new InvalidParameterException();
        }
    }

    public List<ClientFeed> readAllFeed(){
        return clientFeedRepository.findAll();
    }

    public Optional<ClientFeed> readFeed(Long feedId){
        Optional<ClientFeed> feed=clientFeedRepository.findById(feedId);
        if (feed.isPresent()){
            return feed;
        }else {
            throw new DataNotFoundException();
        }
    }

    @Transactional
    public void patchFeed(Long feedId, FeedRequestDto requestDto){
        final Optional<ClientFeed> clientFeed= clientFeedRepository.findById(feedId);
        try {
            if(clientFeed.isPresent()){
                if(requestDto.getTitle()!=null){
                    clientFeed.get().setTitle(requestDto.getTitle());
                }
                if(requestDto.getBody()!=null){
                    clientFeed.get().setBody(requestDto.getBody());
                }
                if(requestDto.getItemId()!=null){
                    clientFeed.get().setItemId(requestDto.getItemId());
                }
                clientFeedRepository.save(clientFeed.get());
            }else {
                throw new DataNotFoundException();
            }

        }catch (IllegalArgumentException e){
            throw new InvalidParameterException();
        }
    }

    public void deleteFeed(Long feedId){
        Optional<ClientFeed> clientFeed=clientFeedRepository.findById(feedId);
        if(clientFeed.isPresent()){
            clientFeedRepository.delete(clientFeed.get());
        }
    }
}
