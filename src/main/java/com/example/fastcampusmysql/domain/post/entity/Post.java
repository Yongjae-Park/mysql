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

    /*
        TODO : 1초에 한번씩 likeCount 값을 주기적으로 넣어줌.
        조회시에는 현재 count값만 조회함.
        매번 조회시 카운트쿼리 -> 주기적인 카운트쿼리로 업데이트 반영
        조회시 쿼리 2번 -> 조회시 쿼리 1번
     */
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
