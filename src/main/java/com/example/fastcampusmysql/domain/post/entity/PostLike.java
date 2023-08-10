package com.example.fastcampusmysql.domain.post.entity;

import com.example.fastcampusmysql.application.utils.FieldUtils;
import com.example.fastcampusmysql.domain.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostLike {

    /**
     * 좋아요가 어느 게시물에 누가 누른건지는 데이터로 가져야한다.
     * Post에서는 도 참조할 필요 없이
     * select memberId from PostLike
     * where postId = 'gdPeed01'
     * 로 조회하여 해당 게시글을 좋아한 사람들에 대한 정보 가져올 수 있다.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Postlike에서 멤버를 조회(=멤버를 참조) 하는일이 없으니 굳이 외래키 걸어줄 필요 없다.
     */
//    @OneToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "memberId")
//    private Member member;
    private Long memberId;

    /**
     * Postlike에서 게시글을 조회(=게시글을 참조) 하는일이 없으니 굳이 외래키 걸어줄 필요 없다.
     */
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "postId")
//    private Post post;

    private Long postId;
    private LocalDateTime createdAt;

//    @Builder
//    public PostLike(Long id, Long memberId, Long postId, LocalDateTime createdAt) {
//        this.id = id;
//        this.memberId = Objects.requireNonNull(memberId);
//        this.postId = Objects.requireNonNull(postId);
//        // id값이 null일 때만 createdAt이 null일 수 있다.
//        this.createdAt = FieldUtils.getLocalDateTime(createdAt);
//    }
}
