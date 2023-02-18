package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.entity.MemberNicknameHistory;
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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberNicknameHistoryRepositoryTest {

    @Autowired
    MemberNicknameHistoryRepository memberNicknameHistoryRepository;
    @Autowired
    MemberRepository memberRepository;

    Member member;

    MemberNicknameHistory sampleHistory;

    @BeforeEach
    void setUp() {
//        saveMember();
        Member member = Member.builder()
                .nickname("initial")
                .email("first@nickname")
                .birthday(LocalDate.now())
                .build();

        Member savedMember = memberRepository.save(member);

        Optional<Member> findMember = memberRepository.findById(savedMember.getId());
        this.member = findMember.orElseThrow();

//        saveSampleHistory();
    }

    private void saveSampleHistory() {
        MemberNicknameHistory history = MemberNicknameHistory.builder()
                .memberId(this.member.getId())
                .nickname("change")
                .build();

        this.sampleHistory = memberNicknameHistoryRepository.save(history);
    }

    private void saveMember() {
        Member member = Member.builder()
                .nickname("initial")
                .email("first@nickname")
                .birthday(LocalDate.now())
                .build();

        Member savedMember = memberRepository.save(member);

        Optional<Member> findMember = memberRepository.findById(savedMember.getId());
        this.member = findMember.orElseThrow();
    }

    @DisplayName("회원이 닉네임을 변경한 횟수를 조회한다.")
    @Test
    void findAllByMemberId_Test() {
        //member.changeNickname("firstTime")

        int TC = 3;

        for (int i = 0; i < TC; i++) {
            member.changeNickname("change" + i);

            MemberNicknameHistory history = MemberNicknameHistory.builder()
                            .memberId(member.getId())
                            .nickname(member.getNickname())
                            .build();
            memberNicknameHistoryRepository.save(history);
        }

        List<MemberNicknameHistory> histories = memberNicknameHistoryRepository.findAllByMemberId(member.getId());

        assertThat(histories.size()).isEqualTo(TC);
    }

    @DisplayName("회원의 닉네임 변경 후 저장된 회원의 닉네임과 일치한다.")
    @Test
    void save_Test() {
        String changeNickname = "change";
        member.changeNickname(changeNickname);

        MemberNicknameHistory history = MemberNicknameHistory.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();

        MemberNicknameHistory savedHistory = memberNicknameHistoryRepository.save(history);

        assertThat(savedHistory.getNickname()).isEqualTo(changeNickname);
    }

    @DisplayName("MemberNicknameHistory는 갱신을 지원하지 않는다.")
    @Test
    void save_UnsupportedOperationException_Test() {
        MemberNicknameHistory history = MemberNicknameHistory.builder()
                .id(1L)
                .memberId(member.getId())
                .nickname("change")
                .build();

        assertThrows(UnsupportedOperationException.class, () -> {
            memberNicknameHistoryRepository.save(history);
        });
    }
}