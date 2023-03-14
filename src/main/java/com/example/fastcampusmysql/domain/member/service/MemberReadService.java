package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.member.repository.MemberJpaRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberNickNameHistoryJpaRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberReadService {

    final private MemberJpaRepository memberJpaRepository;
    final private MemberNickNameHistoryJpaRepository memberNickNameHistoryJpaRepository;
    public MemberDto getMember(Long id) {
        Member member = memberJpaRepository.findById(id).orElseThrow();
        return Member.toDto(member);
    }

    public MemberDto getMemberByEmail(String email) {
        Member member = memberJpaRepository.findByEmail(email).orElseThrow();
        return Member.toDto(member);
    }

    public List<MemberDto> getMembers(List<Long> ids) {
        List<Member> members = memberJpaRepository.findAllByIdIn(ids);
        return members
                .stream()
                .map(Member::toDto)
                .toList();
    }

    public List<MemberNicknameHistoryDto> getNickNameHistories(Long memberId) {
        return memberNickNameHistoryJpaRepository.findAllByMemberId(memberId)
                .stream()
                .map(MemberNicknameHistory::toDto)
                .toList();
    }
}


