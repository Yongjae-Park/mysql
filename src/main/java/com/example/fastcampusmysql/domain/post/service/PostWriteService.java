package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.member.repository.MemberJpaRepository;
import com.example.fastcampusmysql.domain.member.repository.MemberRepository;
import com.example.fastcampusmysql.domain.post.dto.PostDto;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostJpaRepository;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostWriteService {
    /*
        TODO: 게시물 회원 검증
     */
    final private PostJpaRepository postJpaRepository;

    final private PostRepository postRepository;

    final private MemberJpaRepository memberJpaRepository;

    @Transactional
    public Long create(PostDto postDto) {
        Member findMember = memberJpaRepository.findById(postDto.memberId()).orElseThrow();

        Post post = Post.builder()
                .member(findMember)
                .contents(postDto.contents())
                .likeCount(0L)
                .build();

        return postJpaRepository.save(post).getId();
    }

    //TODO : 낙관적 락 좋아요기능 JpaRepository 사용하여 구현
    @Transactional
    public void likePost(Long postId) {
        Post post = postJpaRepository.findById(postId).orElseThrow();
        post.incrementLikeCount();
        postJpaRepository.save(post);
    }

    public void likePostByOptimisticLock(Long postId) {
        Post post = postJpaRepository.findById(postId).orElseThrow();
        post.incrementLikeCount();
        postJpaRepository.save(post);
    }
}
