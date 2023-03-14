package com.example.fastcampusmysql.domain.member.entity;

import com.example.fastcampusmysql.application.utils.FieldUtils;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String nickname;

    private String email;

    private LocalDate birthday;

    private LocalDateTime createdAt;

    private static Long NAME_MAX_LENGTH = 10L;

    @Builder
    public Member(Long id, String nickname, String email, LocalDate birthday, LocalDateTime createdAt) {
        this.id = id;
        this.email = Objects.requireNonNull(email);
        this.birthday = Objects.requireNonNull(birthday);
        this.nickname = Objects.requireNonNull(nickname);
        this.createdAt = FieldUtils.getLocalDateTime(createdAt);
    }

    public void changeNickname(String to) {
        Objects.requireNonNull(to);
        nickname = to;
    }

    public static MemberDto toDto(Member member) {
        return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getBirthday());
    }

}
