package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampusmysql.domain.member.repository.MemberJpaRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberNickNameHistoryJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class MemberWriteServiceTest {

    @Autowired
    MemberWriteService memberWriteService;

    @Autowired
//    MemberRepository memberRepository;
    MemberJpaRepository memberJpaRepository;

    @Autowired
//    MemberNicknameHistoryRepository memberNicknameHistoryRepository;
    MemberNickNameHistoryJpaRepository memberNickNameHistoryJpaRepository;

    MemberDto.RegisterMemberCommand memberDto;

    @BeforeEach
    void setUp() {
        String email = "ytothej92@gmail.com";
        String nickname = "yongjae";
        LocalDate birthDay = LocalDate.of(1992, 4, 14);

        memberDto = new MemberDto.RegisterMemberCommand(email, nickname, birthDay);
    }

    @DisplayName("회원 등록 후 조회한 id가 같다.")
    @Test
    void register_Test() {
        Member registeredMember = memberWriteService.register(memberDto);

        Optional<Member> findMember = memberJpaRepository.findById(registeredMember.getId());

        assertThat(findMember.get().getId()).isEqualTo(registeredMember.getId());
    }


    @DisplayName("회원 이름이 변경되어 저장된다.")
    @Test
    void changeNickname_Test() {
        Member registeredMember = memberWriteService.register(memberDto);
        String changeNickname = "jaeyong";

        memberWriteService.changeNickname(registeredMember.getId(), changeNickname);

        Optional<Member> findMember = memberJpaRepository.findById(registeredMember.getId());
        //변경된 것 확인
        //히스토리 확인
        assertThat(findMember.get().getNickname()).isEqualTo(changeNickname);
    }

    @DisplayName("회원 이름이 변경되어 내역이 저장된다.")
    @Test
    void changeNickname_History_Test() {
        Member registeredMember = memberWriteService.register(memberDto);
        String changeNickname = "jaeyong";

        memberWriteService.changeNickname(registeredMember.getId(), changeNickname);

        Optional<Member> findMember = memberJpaRepository.findById(registeredMember.getId());

        List<MemberNicknameHistory> histories = memberNickNameHistoryJpaRepository.findAllByMemberId(findMember.get().getId());

        MemberNicknameHistory history = histories.get(0);

        assertThat(history.getNickname()).isEqualTo(changeNickname);
    }
}