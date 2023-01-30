package com.example.fastcampusmysql.domain.follow.dto;

import java.time.LocalDateTime;

public record FollowDto(
        Long fromMemberId,
        Long toMemberId
) {
}
