package com.example.fastcampusmysql.domain.follow.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Follow {
    /*
        정규화 고민 포인트
        1. 100만명의 팔로워를 가진 인플루언서가 닉네임을 변경하면 해당 인플루언서를 팔료잉하는 유저의 레코드를 전부 업데이트를 해야 하므로
        정규화가 필요하다.
        2. 관련된 연관 데이터를 어떻게 가져올 것인지
            1. 조인 (조인은 가능하면 미루는게 좋다.)
                팔로우 서비스에 멤버가 들어가기 때문에 엄청난 결합이 생긴다.
                결합이 강하기 때문에 추후에 리팩토링이 힘들 수 있다.
                초기엔 결합을 낮추는 방향으로 진행하는 것이 좋다.
            2. 쿼리 한번 더 날리기
            3. 별도 테이블을 만든다.
     */
    final private Long id;

    final private Long fromMemberId;

    final private Long toMemberId;

    final private LocalDateTime createdAt;

    @Builder
    public Follow(Long id, Long fromMemberId, Long toMemberId, LocalDateTime createdAt) {
        this.id = id;
        this.fromMemberId = Objects.requireNonNull(fromMemberId);
        this.toMemberId = Objects.requireNonNull(toMemberId);
        //TODO : 해당 createdAt 값 가져오는 과정이 중복코드가 됐음. util로 빼내는 작업
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }
}
