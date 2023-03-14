package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberNickNameHistoryJpaRepository extends JpaRepository<MemberNicknameHistory, Long> {

    Optional<MemberNicknameHistory> findByMemberIdAndNickname(Long memberId, String nickname);
    List<MemberNicknameHistory> findAllByMemberId(Long memberId);
}
