package com.onjung.onjung.user.domain;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "User")
@DynamicInsert
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 20, nullable = false)
    private String email;

//    Social Login 에서 받아오는 문자열값
    @Column(length = 32, nullable = false)
    private String uuid;

//    주소 Table에서 입력한 후 받아오는 값
    @NotNull
    private String location_id;

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
    @ColumnDefault("기본 이미지")
    private String profileImg;

//    프로필 소개
    @Column(name ="profile_intro", length = 256)
    @ColumnDefault("안녕하세요:)")
    private String profileIntro;

//    전화번호
    @Column(length = 30, nullable = false)
    private String phone;

//    휴면 계정 여부(DEFAULT = 0)
    @ColumnDefault("0")
    @Column(name ="is_active")
    private Boolean isActive;

//    계정 정지 여부 (DEFAULT = 0)
    @ColumnDefault("0")
    @Column(name ="is_blocked")
    private Boolean isBlocked;

//    사용자 닉네임 (UNIQUE)
    @Column(length = 20, unique = true, nullable = false)
    private String username;

//    생년월일
    @NotNull
    private LocalDateTime birth;

//    대학교
    @Column(length = 20, nullable = false)
    private String university;

//    대학생 인증 여부
    @ColumnDefault("0")
    @Column(name ="is_university")
    private Boolean isUniversity;

    @Builder
    public void saveUserEntitiy(
                String email,
                String uuid,
                String location_id,
                String provider,
                String profileImg,
                String profileIntro,
                String phone,
                String username,
                LocalDateTime birth,
                String university) {

        this.email = email;
        this.uuid = uuid;
        this.location_id = location_id;
        this.provider = provider;
        this.profileImg = profileImg;
        this.profileIntro = profileIntro;
        this.phone = phone;
        this.username = username;
        this.birth = birth;
        this.university = university;
    }
}
