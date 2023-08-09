package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional(readOnly = true)
class MemberRepositoryTest {

    @Autowired
//    private MemberRepository memberRepository;
    private MemberJpaRepository memberJpaRepository;

    private int COUNT = 4;

    List<Member> memberDatas;
    List<String> names;

    Long id = 1L;

    @BeforeEach
    @Transactional
    void setUp() {
        memberDatas = LongStream.range(1, COUNT + 1)
                .mapToObj(number ->
                        Member.builder()
                                .id(number)
                                .email("aa")
                                .nickname("yj" + number)
                                .birthday(LocalDate.of(1992, 04, 14))
                                .build())
                .collect(Collectors.toList());

        names = memberDatas.stream()
                .map(Member::getNickname)
                .collect(Collectors.toList());

        memberJpaRepository.saveAll(memberDatas);
    }

    @DisplayName("추가한 세명의 멤버를 저장한 후 전체 조회 시 사이즈 값이 COUNT + 3이다.")
    @Test
    @Transactional
    @Rollback(value = false)
    public void saveAll_Test() {
        Member member1 = createMemberWithNickName("yongjae1");
        Member member2 = createMemberWithNickName("yongjae2");
        Member member3 = createMemberWithNickName("yongjae3");

        List<Member> members = new ArrayList<>();

        members.add(member1);
        members.add(member2);
        members.add(member3);

        memberJpaRepository.saveAll(members);

        List<Member> allMembers = memberJpaRepository.findAll();

        assertThat(allMembers.size()).isEqualTo(COUNT + 3);
    }

    @DisplayName("멤버 인스턴스의 id와 생성 후 조회한 멤버 id 값이 같다.")
    @Test
    void findById_Test() {
        Member member = createMember();

        Optional<Member> findMember = memberJpaRepository.findById(member.getId());

        assertThat(findMember.orElseThrow().getId()).isEqualTo(member.getId());
    }

    private Member createMember() {
        Member member = Member.builder()
                .nickname("yongjae")
                .email("yongjae@gmail.com")
                .birthday(LocalDate.of(1992,04,14))
                .build();

        return memberJpaRepository.save(member);
    }

    private Member createMemberWithNickName(String nickName) {
        Member member = Member.builder()
                .nickname("yongjae")
                .email("yongjae@gmail.com")
                .birthday(LocalDate.of(1992,04,14))
                .build();

        return member;
    }

    @DisplayName("없는 id의 경우 Optional.empty를 반환받는다.")
    @Test()
    void findById_InvalidId_Test() {
        Optional<Member> findMember = memberJpaRepository.findById(-1L);

        assertTrue(findMember.isEmpty());
    }

    @DisplayName("IN조건 만족하는 멤버를 전체 조회한다.")
    @Test
    void findAllByIdIn_Test() {
        List<Long> ids = new ArrayList<>();

        Long start = memberJpaRepository.findAll().get(0).getId();
        int tc = 3;

        for (int i = 1; i <= tc; i++) {
            ids.add(Long.valueOf(start + i));
        }

        List<Member> findMembers = memberJpaRepository.findAllByIdIn(ids);

        assertThat(findMembers.size()).isEqualTo(tc);
    }

    @DisplayName("조회한 전체 멤버 수가 초기 셋팅한 멤버의 수와 같다.")
    @Test
    void findAll_Test() {
        List<Member> findMembers = memberJpaRepository.findAll();

        int findMemberSize = findMembers.size();

        assertThat(findMemberSize).isEqualTo(COUNT);
    }

    @DisplayName("생성한 멤버의 id와 생성 후 멤버의 id가 같다.")
    @Test
    void save_Test() {
        Member member = createMember();

        Member savedMember = memberJpaRepository.save(member);

        assertThat(member.getId()).isEqualTo(savedMember.getId());
    }
}