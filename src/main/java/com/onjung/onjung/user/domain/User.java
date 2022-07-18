package com.onjung.onjung.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String profileImg;

//    프로필 소개
    @Column(name ="profile_intro", length = 256)
    private String profileIntro;

//    전화번호
    @Column(length = 30, nullable = false)
    private String phone;

//    휴면 계정 여부(DEFAULT = 0)
    @Column(name ="is_active", columnDefinition = "boolean default false")
    private Boolean isActive;

//    계정 정지 여부 (DEFAULT = 0)
    @Column(name ="is_blocked", columnDefinition = "boolean default false")
    private Boolean isBlocked;

//    사용자 닉네임 (UNIQUE)
    @Column(length = 20, unique = true, nullable = false)
    private String username;

//    생년월일
    @NotNull
    private LocalDate birth;

//    대학교
    @Column(length = 20, nullable = false)
    private String university;

//    대학생 인증 여부
    @Column(name ="is_university", columnDefinition = "boolean default false")
    private Boolean isUniversity;

    @Builder
    public User(
                String email,
                String uuid,
                String location_id,
                String provider,
                String profileImg,
                String profileIntro,
                String phone,
                String username,
                LocalDate birth,
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
