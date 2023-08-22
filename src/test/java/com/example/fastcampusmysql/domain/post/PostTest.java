package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.domain.post.entity.Post;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class PostTest {

    @Test
    public void 콘텐츠변경후_버전증가() {
        Long version = 10L;
        //멤버 생성
        Member member = createMember();
        //게시물 생성
        Post post = new Post(0L, member, "신발팔아요", null, 0L, version, null);
        //게시물 콘텐츠 업데이트
        post.changeContents("신발판매완료");
        //버전 확인
        assertThat(post.getVersion()).isEqualTo(version + 1);
    }

    @Test
    public void 콘텐츠_변경() {
        String originalContents = "신발팔아요";
        //멤버 생성
        Member member = createMember();
        //게시물 생성
        Post post = new Post(0L, member, originalContents, null, 0L, 0L, null);
        //게시물 업데이트
        String updateContents = "신발판매완료";
        post.changeContents(updateContents);

        assertThat(post.getContents()).isNotEqualTo(originalContents);
    }

    private Member createMember() {
        Member member = Member.builder()
                .nickname("yongjae")
                .email("yongjae@gmail.com")
                .birthday(LocalDate.of(1992,04,14))
                .build();

        return member;
    }
}
