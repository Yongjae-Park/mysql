package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.application.common.exception.member.DuplicateEmailException;
import com.example.fastcampusmysql.application.common.exception.member.DuplicateNicknameException;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.member.repository.MemberJpaRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberNickNameHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberWriteService {
    /**
     *  TODO: 회원 등록 검증 spring security
     *  oAuth
     */

    final private MemberJpaRepository memberJpaRepository;

    final private MemberNickNameHistoryJpaRepository memberNickNameHistoryJpaRepository;
    @Transactional
    public Member register(MemberDto.RegisterMemberCommand command) {
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

        if (checkDuplicateNickname(command.nickname())) {
             throw new DuplicateNicknameException("닉네임 중복");
        }

        if (checkDuplicateEmail(command.email())) {
            throw new DuplicateEmailException("이메일 중복");
        }

        Member savedMember =  memberJpaRepository.save(member);
//        int zero = 0 / 0;

        saveMemberNicknameHistory(savedMember);
        return savedMember;
    }

    private boolean checkDuplicateNickname(String nickname) {
        return memberJpaRepository.existsByNickname(nickname);
    }

    private boolean checkDuplicateEmail(String email) {
        return memberJpaRepository.existsByEmail(email);
    }


    public void changeNickname(Long memberId, String nickname) {
        /*
            1. 회원의 이름을 변경
            2. 변경 내역을 저장한다.
         */
        Member member = memberJpaRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);

        saveMemberNicknameHistory(member);

        memberJpaRepository.save(member);
    }

    private void saveMemberNicknameHistory(Member member) {
        MemberNicknameHistory history = MemberNicknameHistory
                .builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

        //TODO : 이미 있는 history id일 경우 exception 발생
        memberNickNameHistoryJpaRepository.save(history);
    }


}
