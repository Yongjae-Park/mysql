package com.example.fastcampusmysql.domain.member.entity;

import com.example.fastcampusmysql.application.utils.FieldUtils;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class MemberNicknameHistory {

    final private Long id;

    final private Long memberId;
    final private String nickname;

    final private LocalDateTime createdAt;

    @Builder
    public MemberNicknameHistory(Long id, Long memberId, String nickname, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.nickname = Objects.requireNonNull(nickname);
        this.createdAt = FieldUtils.getLocalDateTime(createdAt);
    }

}
