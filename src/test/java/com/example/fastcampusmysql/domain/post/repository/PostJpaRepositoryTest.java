package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberJpaRepository;
import com.example.fastcampusmysql.domain.post.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class PostJpaRepositoryTest {

    @Autowired
    private PostJpaRepository postJpaRepository;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    private Member member;
    @BeforeEach
    void setUp() {
        Member member = Member.builder()
                .nickname("test")
                .email("test@gmail.com")
                .birthday(LocalDate.of(2023,01,01))
                .build();

        this.member = memberJpaRepository.save(member);
    }

    @DisplayName("게시물 등록 실패 - 등록되지 않은 회원일 경우 게시물이 생성되지 않는다.")
    @Test
    void fail_save_optional_property_Test() {
        Member member = Member.builder()
                .nickname("invalid")
                .email("invalid@gmail.com")
                .birthday(LocalDate.of(1992, 4, 14))
                .build();

        Post post = Post.builder()
                .member(member)
                .contents("temp")
                .build();

        assertThrows(InvalidDataAccessApiUsageException.class, () ->
                postJpaRepository.save(post));
    }

    @DisplayName("게시물 등록 성공한다.")
    @Test
    void save_Test() {
        Post post = Post.builder()
                .contents("temp")
                .member(this.member)
                .build();

        Post savedPost = postJpaRepository.save(post);

        assertThat(savedPost.getMember().getId()).isEqualTo(this.member.getId());
    }

    @DisplayName("회원 정보로 게시물을 전부 조회한다.")
    @Test
    void findByMemberId() {
        createPosts();

        List<Post> findPosts = postJpaRepository.findByMember(this.member);

        assertThat(findPosts.size()).isEqualTo(2);
    }

    private void createPosts() {
        List<Post> posts = new ArrayList<>();
        Post first_post = Post.builder()
                .member(this.member)
                .contents("test")
                .build();

        Post second_post = Post.builder()
                .member(this.member)
                .contents("test2")
                .build();

        posts.add(first_post);
        posts.add(second_post);

        postJpaRepository.saveAll(posts);
    }
}
