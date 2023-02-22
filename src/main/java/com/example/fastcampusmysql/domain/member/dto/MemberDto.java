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
                @Size(max = 10, message = "nickname�� 10�� ���Ϸ� �����մϴ�.")
                String nickname,
                LocalDate birthday
        ) {
        }

}
