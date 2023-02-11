package com.example.fastcampusmysql.domain.post.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record DailyPostCount(
        Long memberId,
        LocalDate date,
        Long postCount) {
}
