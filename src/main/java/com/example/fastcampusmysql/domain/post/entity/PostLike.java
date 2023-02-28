package com.example.fastcampusmysql.domain.post.entity;

import com.example.fastcampusmysql.application.utils.FieldUtils;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class PostLike {
    final private Long id;
    final private Long memberId;

    final private Long postId;

    final private LocalDateTime createdAt;

    @Builder
    public PostLike(Long id, Long memberId, Long postId, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.postId = Objects.requireNonNull(postId);
        // id값이 null일 때만 createdAt이 null일 수 있다.
        this.createdAt = FieldUtils.getLocalDateTime(createdAt);
    }
}
