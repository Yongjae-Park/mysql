package com.example.fastcampusmysql.domain.member.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    private int COUNT = 1000;

    List<Member> memberDatas;
    List<String> names;

    Long id = 1L;

    @BeforeEach
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

        memberRepository.saveAll(memberDatas);
    }

    @Test
    public void saveAllTest() {
        Member member1 = new Member(1L, "yy", "@", LocalDate.of(1992, 4, 14), null);
        Member member2 = new Member(2L, "yy", "@", LocalDate.of(1992, 4, 14), null);
        Member member3 = new Member(3L, "yy", "@", LocalDate.of(1992, 4, 14), null);

        List<Member> members = new ArrayList<>();

        members.add(member1);
        members.add(member2);
        members.add(member3);

        memberRepository.saveAll(members);
    }

    @DisplayName("멤버 인스턴스의 id와 생성 후 멤버 id 값이 같다.")
    @Test
    void findByIdTest() {
        Member member = Member.builder()
                .id(id)
                .nickname("yongjae")
                .email("yongjae@gmail.com")
                .birthday(LocalDate.of(1992,04,14))
                .build();

        Member savedMember = memberRepository.save(member);

        assertThat(savedMember.getId()).isEqualTo(id);
    }

    @DisplayName("IN조건 만족하는 멤버를 전체 조회한다.")
    @Test
    void findAllByIdIn_Test() {
        List<Long> ids = new ArrayList<>();

        Long start = memberRepository.findAll().get(0).getId();
        int tc = 3;

        for (int i = 1; i <= tc; i++) {
            ids.add(Long.valueOf(start + i));
        }

        List<Member> findMembers = memberRepository.findAllByIdIn(ids);

        assertThat(findMembers.size()).isEqualTo(3);
    }

    @DisplayName("조회한 전체 멤버 수가 초기 셋팅한 멤버의 수와 같다.")
    @Test
    void findAll_Test() {
        List<Member> findMembers = memberRepository.findAll();

        int findMemberSize = findMembers.size();

        assertThat(findMemberSize).isEqualTo(COUNT);
    }

    @DisplayName("생성한 멤버의 id와 생성 후 멤버의 id가 같다.")
    @Test
    void save_Test() {
        Member member = Member.builder()
                .id(id)
                .nickname("ytothej92")
                .email("ytothej92@gmail.com")
                .birthday(LocalDate.of(1992,04,14))
                .build();

        Member savedMember = memberRepository.save(member);

        assertThat(member.getId()).isEqualTo(savedMember.getId());

    }
}