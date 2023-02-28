package com.example.fastcampusmysql.application.controller;

import com.example.fastcampusmysql.application.usecase.CreatePostLikeUsecase;
import com.example.fastcampusmysql.application.usecase.CreatePostUsecase;
import com.example.fastcampusmysql.application.usecase.GetTimeLinePostUsecase;
import com.example.fastcampusmysql.application.utils.CursorRequest;
import com.example.fastcampusmysql.application.utils.PageCursor;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.dto.PostDto;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.PostWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    final private PostWriteService postWriteService;
    final private PostReadService postReadService;

    final private GetTimeLinePostUsecase getTimeLinePostUsecase;

    final private CreatePostUsecase createPostUsecase;

    final private CreatePostLikeUsecase createPostLikeUsecase;

    @PostMapping("")
    public Long register(PostDto postDto) {
        return createPostUsecase.execute(postDto);
    }

    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(DailyPostCountRequest request) {
        return postReadService.getDailyPostCounts(request);
    }

    @GetMapping("/members/{memberId}")
    public Page<PostDto> getPost(
            @PathVariable Long memberId,
            Pageable pageable
    ) {
        return postReadService.getPosts(memberId, pageable);
    }

    @GetMapping("/members/{memberId}/by-cursor")
    public PageCursor<Post> getPostByCursor(
            @PathVariable Long memberId,
            CursorRequest cursorRequest
    ) {
        return postReadService.getPosts(memberId, cursorRequest);
    }

    @GetMapping("/memers/{memberId}/timeline")
    public PageCursor<Post> getTimeline(
            @PathVariable Long memberId,
            CursorRequest cursorRequest
    ) {
        return getTimeLinePostUsecase.executeByTimeLine(memberId, cursorRequest);
    }

    @PostMapping("{postId}/like")
    public void likePost(@PathVariable Long postId) {
        postWriteService.likePost(postId);
    }

    @PostMapping("{postId}/like/v2")
    public void likePostV2(@PathVariable Long postId) {
        postWriteService.likePostByOptimisticLock(postId);
    }

    @PostMapping("/{postId}/like/v3")
    public void likePostV3(@PathVariable Long postId, @RequestParam Long memberId) {
        createPostLikeUsecase.execute(postId, memberId);
    }
}
