package com.example.fastcampusmysql.domain.member.entity;

import com.example.fastcampusmysql.application.utils.FieldUtils;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor
public class MemberNicknameHistory {

    @Id
    @GeneratedValue
    private Long id;

    private Long memberId;
    private String nickname;

    private LocalDateTime createdAt;

    @Builder
    public MemberNicknameHistory(Long id, Long memberId, String nickname, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.nickname = Objects.requireNonNull(nickname);
        this.createdAt = FieldUtils.getLocalDateTime(createdAt);
    }

    public static MemberNicknameHistoryDto toDto(MemberNicknameHistory history) {
        return new MemberNicknameHistoryDto(
                history.getId(),
                history.getMemberId(),
                history.getNickname(),
                history.getCreatedAt()
        );
    }
}
