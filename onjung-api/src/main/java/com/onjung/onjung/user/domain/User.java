package com.onjung.onjung.user.domain;

//import com.onjung.onjung.domain.Chat;
import com.onjung.onjung.feed.domain.ClientFeed;
import com.onjung.onjung.feed.domain.ServerFeed;
import com.onjung.onjung.review.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "User")
@DynamicInsert
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //    사용자 닉네임 (UNIQUE)
    @Column(length = 100, unique = true, nullable = false)
    private String username;

    //비밀번호, OAUTH 구현이후 삭제할지는 고민 필요
    @Column(length = 100, nullable = false)
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="writer_id")
    private List<ClientFeed> clientFeedList = new ArrayList<>();

    @BatchSize(size = 100)
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="writer_id")
    private List<ServerFeed> serverFeedList = new ArrayList<>();

    // 작성한 리뷰 목록
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="sender_id")
    private List<Review> sentReviewList = new ArrayList<>();

    // 받은 리뷰 목록
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="receiver_id")
    private List<Review> receievedReviewList = new ArrayList<>();

    // 유저가 보낸 채팅 목록 (말풍선 하나하나)
//    @OneToMany(cascade = CascadeType.ALL)
//    @JoinColumn(name="sender_id")
//    private List<Chat> sentChatList = new ArrayList<>();


    @Column(length = 20, nullable = false)
    private String email;

//    Social Login 에서 받아오는 문자열값
    @Column(length = 32, nullable = false)
    private String uuid;

//    주소 Table에서 입력한 후 받아오는 값, 이후 수정 필요
    @NotNull
    @Column(name = "location_id")
    private String locationId;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

//    소셜로그인 제공사
    @Column(length = 20, nullable = false)
    private String provider;

//    프로필 이미지
    @Column(name ="profile_img", length = 256)
    private String profileImg;

//    프로필 소개
    @Column(name ="profile_intro", length = 256)
    private String profileIntro;

//    전화번호
    @Column(length = 30, nullable = false)
    private String phone;

//    휴면 계정 여부(DEFAULT = 1)
    @Column(name ="is_active")
    private Boolean isActive;

//    계정 정지 여부 (DEFAULT = 0)
    @Column(name ="is_blocked")
    private Boolean isBlocked;

//    생년월일
    @NotNull
    private LocalDate birth;

//    대학교
    @Column(length = 20, nullable = false)
    private String university;

//    대학생 인증 여부 (DEFAULT = 0)
    @Column(name ="is_university")
    private Boolean isUniversity;

//    누적 경고수, 10회 이상일시 block
    @Column(name = "report_cnt", columnDefinition = "bigint default 0")
    private long reportCnt;

//    나눔 수 (포인트)
    @Column(columnDefinition = "bigint default 0")
    private long point;

//    마지막 로그인 날짜
    @CreationTimestamp
    @Column(name = "last_logined")
    private LocalDateTime lastLogined;

    @ColumnDefault("0")
    private float rate;

    @ColumnDefault("0")
    @Column(name = "rate_sum")
    private float rateSum;

    @ColumnDefault("0")
    @Column(name = "review_cnt")
    private int reviewCnt;

    // 찜한 client 피드 아이디 목록
    @Column(name = "client_feed_like")
    private String clientFeedLike;

    // 찜한 server 피드 아이디 목록
    @Column(name = "server_feed_like")
    private String serverFeedLike;

    @Column(name = "student_card")
    private String studentCard;

    @PrePersist
    public void setDefault(){
        this.isActive = this.isActive == null ? true : this.isActive;
        this.isBlocked = this.isBlocked == null ? false : this.isBlocked;
        this.isUniversity = this.isUniversity == null ? false : this.isUniversity;
    }

    @Builder
    public User(
                String email,
                String password,
                String uuid,
                String locationId,
                String provider,
                String profileImg,
                String profileIntro,
                String phone,
                String username,
                LocalDate birth,
                String university) {

        this.email = email;
        this.password=password;
        this.uuid = uuid;
        this.locationId = locationId;
        this.provider = provider;
        this.profileImg = profileImg;
        this.profileIntro = profileIntro;
        this.phone = phone;
        this.username = username;
        this.birth = birth;
        this.university = university;
    }

    //lastLogin 업데이트, 이후 로그인 구현시 테스트 필요
    public void setLastLogin(){
        this.lastLogined= LocalDateTime.now();
    }

    public void changeIsActive(){
        this.isActive= !this.isActive;
    }

    public void changeIsBlocked(){
        this.isBlocked= !this.isBlocked;
    }

    public void earnPoints(){
        this.point+=1;
    }

    public void discountPoints(){
        this.point-=1;
    }

    public String getUsername(){
        return this.username;
    }

    public String getPassword(){
        return this.password;
    }

    public void updateRate(float rate){

        this.rateSum = this.rateSum + rate;
        this.reviewCnt = this.reviewCnt + 1;
        this.rate = this.rateSum / this.reviewCnt;

    }
}
