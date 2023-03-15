package com.example.fastcampusmysql.domain.post.dto;

import com.example.fastcampusmysql.domain.member.entity.Member;

import java.time.LocalDateTime;

public record PostDto(
//        Long memberId,
        Member member,
        String contents,
        LocalDateTime createdAt,
        Long likeCount) {

}
