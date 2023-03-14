package com.example.fastcampusmysql.domain.member.service;

import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberJpaRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberNickNameHistoryJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberReadServiceTest {

    @Autowired
    MemberReadService memberReadService;

    @Autowired
    MemberWriteService memberWriteService;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Autowired
    MemberNickNameHistoryJpaRepository memberNickNameHistoryJpaRepository;

    @DisplayName("ȸ�� Id �� ����� ��ȸ�Ѵ�.")
    @Test
    void getMember_Test() {
        Member member = createRegisterMemberCommand().toEntity();
        Member savedMember = memberJpaRepository.save(member);

        MemberDto getMemberDto = memberReadService.getMember(savedMember.getId());

        assertThat(savedMember.getNickname()).isEqualTo(getMemberDto.nickname());
    }

    private MemberDto.RegisterMemberCommand createRegisterMemberCommand() {
        MemberDto.RegisterMemberCommand command = MemberDto.RegisterMemberCommand.builder()
                .nickname("yongjae")
                .email("yongjae@gmail.com")
                .birthday(LocalDate.of(1992, 4, 14))
                .build();

        return command;
    }

    @DisplayName("��ȸ ���� - ��ȿ���� ���� ȸ�� Id�� ��ȸ �����Ѵ�.")
    @Test
    void getMember_fail() {
        Long wrongMemberId = -1L;

        assertThrows(NoSuchElementException.class, () ->
                memberReadService.getMember(wrongMemberId));
    }

    @Test
    void getMemberByEmail() {
    }

    @DisplayName("ȸ�� Id����Ʈ�� ȸ�� �������� ��ȸ�Ѵ�.")
    @Test
    void getMembers_Test() {
        List<Long> ids = new ArrayList<>();
        List<Member> members = new ArrayList<>();

        Member member_1 = createRegisterMemberCommand().toEntity();
        Member member_2 = createRegisterMemberCommand().toEntity();
        Member member_3 = createRegisterMemberCommand().toEntity();

        members.add(member_1);
        members.add(member_2);
        members.add(member_3);

        List<Member> findMembers = memberJpaRepository.saveAll(members);

        for (int i = 0; i < 3; i++) {
            ids.add(findMembers.get(i).getId());
        }

        List<MemberDto> getMemberDtos = memberReadService.getMembers(ids);

        assertThat(getMemberDtos.size()).isEqualTo(ids.size());
    }

    @DisplayName("�г��� �����̷��� ��ȸ�Ѵ�.")
    @Test
    void getNickNameHistories_Test() {
        MemberDto.RegisterMemberCommand memberCommand = createRegisterMemberCommand();
        Member savedMember = memberWriteService.register(memberCommand);

        List<MemberNicknameHistoryDto> historyDtos = memberReadService.getNickNameHistories(savedMember.getId());

        assertThat(historyDtos.size()).isEqualTo(1);
    }

}