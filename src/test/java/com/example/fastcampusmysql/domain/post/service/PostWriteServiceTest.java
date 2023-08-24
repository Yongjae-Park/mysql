package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberJpaRepository;
import com.example.fastcampusmysql.domain.post.dto.PostDto;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class PostWriteServiceTest {

    @Autowired
    PostWriteService postWriteService;

    @Autowired
    PostJpaRepository postJpaRepository;

    @Autowired
    MemberJpaRepository memberJpaRepository;

    private Member member;

    @BeforeEach
    void setUp() {
        Member member = createMember();
        this.member = memberJpaRepository.save(member);
    }

    private Member createMember() {
        return Member.builder()
                .nickname("yongjae")
                .email("yongjae@gmail.com")
                .birthday(LocalDate.of(1992, 4, 14))
                .build();
    }

    @Test
    @DisplayName("유효하지 않은 회원Id로 게시물 등록에 실패한다.")
    void fail_create_Test() {
        PostDto postDto = PostDto.builder()
                .memberId(0L)
                .contents("test")
                .likeCount(0L)
                .build();

        assertThrows(NoSuchElementException.class, ()->
                postWriteService.create(postDto));
    }

    @Test
    @DisplayName("게시물 등록 성공")
    void create_Test() {
        PostDto postDto = PostDto.builder()
                .memberId(this.member.getId())
                .contents("test")
                .likeCount(0L)
                .build();

        Long savedPostId = postWriteService.create(postDto);

        Post findPost = postJpaRepository.findById(savedPostId).orElseThrow();

        assertThat(findPost.getId()).isEqualTo(savedPostId);
    }

}