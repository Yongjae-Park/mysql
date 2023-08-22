package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.application.common.exception.member.DuplicateEmailException;
import com.example.fastcampusmysql.application.common.exception.member.DuplicateNicknameException;
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
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberWriteServiceTest {

    @Autowired
    MemberWriteService memberWriteService;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
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

    @DisplayName("회원가입 실패 - 이메일 중복으로 회원가입에 실패한다.")
    @Test
    public void emailDuplicate() {
        MemberDto.RegisterMemberCommand memberCommand = MemberDto.RegisterMemberCommand.builder()
                .email("yyy@gmail.com")
                .nickname("yyy")
                .birthday(LocalDate.of(1992, 4, 14))
                .build();

        MemberDto.RegisterMemberCommand emailDuplicatedCommand = MemberDto.RegisterMemberCommand.builder()
                .email("yyy@gmail.com")
                .nickname("zzz")
                .birthday(LocalDate.of(1992, 4, 14))
                .build();

        memberWriteService.register(memberCommand);

        assertThrows(DuplicateEmailException.class, () -> memberWriteService.register(emailDuplicatedCommand));
    }

    @DisplayName("회원가입 실패 - 이메일 중복으로 회원가입에 실패한다.")
    @Test
    public void nicknameDuplicate() {
        MemberDto.RegisterMemberCommand memberCommand = MemberDto.RegisterMemberCommand.builder()
                .email("yyy@gmail.com")
                .nickname("yyy")
                .birthday(LocalDate.of(1992, 4, 14))
                .build();

        MemberDto.RegisterMemberCommand nicknameDuplicatedCommand = MemberDto.RegisterMemberCommand.builder()
                .email("zzz@gmail.com")
                .nickname("yyy")
                .birthday(LocalDate.of(1992, 4, 14))
                .build();

        memberWriteService.register(memberCommand);

        assertThrows(DuplicateNicknameException.class, () -> memberWriteService.register(nicknameDuplicatedCommand));
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

        MemberNicknameHistory findHistory = memberNickNameHistoryJpaRepository.findByMemberIdAndNickname(registeredMember.getId(), registeredMember.getNickname()).orElseThrow();

        assertThat(findHistory.getNickname()).isEqualTo(changeNickname);
    }
}
