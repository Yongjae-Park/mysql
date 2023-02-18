package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberWriteService {
    /**
     *  TODO: 회원 등록 검증 spring security
     *  oAuth
     */

    final private MemberRepository memberRepository;

    final private MemberNicknameHistoryRepository memberNicknameHistoryRepository;
    public Member register(RegisterMemberCommand command) {
        /*
            목표 - 회원정보(이메일, 닉네임, 생년월일)을 등록한다.
            파라미터 - memberRegisterCommand
            val member = Member.of(memberRegisterCommand)
            memberRepository.save(member)
         */
        Member member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthday(command.birthday())
                .build();
        Member savedMember =  memberRepository.save(member);
//        saveMemberNicknameHistory(savedMember);
        return savedMember;
    }

    public void changeNickname(Long memberId, String nickname) {
        /*
            1. 회원의 이름을 변경
            2. 변경 내역을 저장한다.
         */
        Member member = memberRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);

        saveMemberNicknameHistory(member);

        memberRepository.save(member);
    }

    private void saveMemberNicknameHistory(Member member) {
        MemberNicknameHistory history = MemberNicknameHistory
                .builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

        memberNicknameHistoryRepository.save(history);
    }


}
