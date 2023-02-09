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

    final private LocalDateTime createdAt;

    @Builder
    public Post(Long id, Long memberId, String contents, LocalDate createdDate, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.contents = Objects.requireNonNull(contents);
        this.createdDate = FieldUtils.getLocalDate(createdDate);
//        this.createdDate = createdDate == null ? LocalDate.now() : createdDate;
//        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
        this.createdAt = FieldUtils.getLocalDateTime(createdAt);
    }

}
