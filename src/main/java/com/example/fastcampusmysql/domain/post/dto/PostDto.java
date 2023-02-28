package com.example.fastcampusmysql.domain.post.dto;

import java.time.LocalDateTime;

public record PostDto(
        Long memberId,
        String contents,
        LocalDateTime createdAt,
        Long likeCount) {

}
