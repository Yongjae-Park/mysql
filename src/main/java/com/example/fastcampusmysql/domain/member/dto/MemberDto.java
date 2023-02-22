package com.example.fastcampusmysql.domain.member.dto;

import lombok.Builder;

import javax.validation.constraints.Size;

import java.time.LocalDate;

public record MemberDto(
        Long id,
        String email,
        @Size(max= 10)
        String nickname,
        LocalDate birthDay
) {
        @Builder
        public record RegisterMemberCommand(
                String email,
                @Size(max = 10, message = "nickname은 10자 이하로 설정합니다.")
                String nickname,
                LocalDate birthday
        ) {
        }

}
