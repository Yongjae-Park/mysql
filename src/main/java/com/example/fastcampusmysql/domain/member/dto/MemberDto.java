package com.example.fastcampusmysql.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.Builder;

import javax.validation.constraints.Size;
import java.time.LocalDate;

public record MemberDto(
        Long id,
        String email,
        @Size(max= 10)
        String nickname,
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate birthday
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
