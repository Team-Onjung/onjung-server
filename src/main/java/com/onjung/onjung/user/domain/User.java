package com.onjung.onjung.user.domain;

import com.onjung.onjung.feed.domain.Status;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @Column(length = 20, nullable = false)
    private String email;

//    Social Login 에서 받아오는 문자열값
    @Column(length = 32, nullable = false)
    private String uuid;

//    주소 Table에서 입력한 후 받아오는 값, 이후 수정 필요
    @NotNull
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

//    마지막 로그인 날짜
    @CreationTimestamp
    @Column(name = "last_logined")
    private LocalDateTime lastLogined;

    @PrePersist
    public void setDefault(){
        this.isActive = this.isActive == null ? true : this.isActive;
        this.isBlocked = this.isBlocked == null ? false : this.isBlocked;
        this.isUniversity = this.isUniversity == null ? false : this.isUniversity;
    }

    @Builder
    public User(
                String email,
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
}
