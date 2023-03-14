package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class MemberNicknameHistoryRepositoryTest {

    @Autowired
    MemberNickNameHistoryJpaRepository memberNicknameHistoryJpaRepository;
    @Autowired
    MemberJpaRepository memberJpaRepository;

    @DisplayName("회원 id로 닉네임 변경내역을 조회한다.")
    @Test
    void findAllByMemberId_Test() {
        Member member = saveMember();

        int TC = 3;

        for (int i = 0; i < TC; i++) {
            member.changeNickname("change" + i);

            MemberNicknameHistory history = MemberNicknameHistory.builder()
                            .memberId(member.getId())
                            .nickname(member.getNickname())
                            .build();
            memberNicknameHistoryJpaRepository.save(history);
        }

        List<MemberNicknameHistory> histories = memberNicknameHistoryJpaRepository.findAllByMemberId(member.getId());

        assertThat(histories.size()).isEqualTo(TC);
    }

    @DisplayName("회원의 닉네임 변경 후 저장된 회원의 닉네임과 일치한다.")
    @Test
    void save_Test() {
        String changeNickname = "change";
        Member member = saveMember();
        member.changeNickname(changeNickname);

        MemberNicknameHistory history = MemberNicknameHistory.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

        MemberNicknameHistory savedHistory = memberNicknameHistoryJpaRepository.save(history);

        assertThat(savedHistory.getNickname()).isEqualTo(changeNickname);
    }

    @DisplayName("회원 id와 닉네임으로 닉네임 변경내역을 조회한다.")
    @Test
    void findByMemberIdAndNickname_Test() {
        Member member = saveMember();
        String changeNickname = "changed";

        member.changeNickname(changeNickname);

        MemberNicknameHistory history = MemberNicknameHistory.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();
        memberNicknameHistoryJpaRepository.save(history);

        MemberNicknameHistory findHistory = memberNicknameHistoryJpaRepository.findByMemberIdAndNickname(
                member.getId(), member.getNickname()).orElseThrow();

        assertThat(findHistory.getNickname()).isEqualTo(changeNickname);
    }

    private Member saveMember() {
        Member member = Member.builder()
                .nickname("initial")
                .email("first@nickname")
                .birthday(LocalDate.now())
                .build();

        Member savedMember = memberJpaRepository.save(member);

        return savedMember;
    }

}