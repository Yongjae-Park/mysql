package com.example.fastcampusmysql.domain.post.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record PostDto(
//        String memberEmail,
        Long memberId,
        String contents,
        LocalDateTime createdAt,
        Long likeCount) {

    public record PostRequestDto(){}

    public record PostResponseDto(){

    }

}
