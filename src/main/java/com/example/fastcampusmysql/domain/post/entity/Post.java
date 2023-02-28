package com.example.fastcampusmysql.domain.post.entity;

import com.example.fastcampusmysql.application.utils.FieldUtils;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Post {

    /**
     * TODO : 이미지업로드, 비디오 업로드,
     *  게시물 수정
     */

    final private Long id;

    final private Long memberId;

    final private String contents;

    final private LocalDate createdDate;

    private Long likeCount;

    private Long version;

    final private LocalDateTime createdAt;

    @Builder
    public Post(Long id, Long memberId, String contents, LocalDate createdDate, Long likeCount, Long version, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.contents = Objects.requireNonNull(contents);
        this.createdDate = FieldUtils.getLocalDate(createdDate);
        this.likeCount = likeCount == null ? 0 : likeCount;
        this.version = version == null ? 0 : version;
        this.createdAt = FieldUtils.getLocalDateTime(createdAt);
    }

    public void incrementLikeCount() {
        likeCount += 1;
    }
}
