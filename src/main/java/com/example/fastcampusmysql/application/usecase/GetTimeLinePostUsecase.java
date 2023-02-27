package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.application.utils.CursorRequest;
import com.example.fastcampusmysql.application.utils.PageCursor;
import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.entity.Timeline;
import com.example.fastcampusmysql.domain.post.service.PostReadService;
import com.example.fastcampusmysql.domain.post.service.TimelineReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetTimeLinePostUsecase {
    final private FollowReadService followReadService;
    final private PostReadService postReadService;

    final private TimelineReadService timelineReadService;

    public PageCursor<Post> execute(Long memberId, CursorRequest cursorRequest) {
        /*
            1. memberId -> 해당 멤버의 follow조회
            2. 1번 결과로 게시물 조회
            다건의 회원을 받아서 게시물을 조회해야함
         */
        List<Follow> followings = followReadService.getFollowings(memberId);
        List<Long> followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();

        return postReadService.getPosts(followingMemberIds, cursorRequest);
    }

    public PageCursor<Post> executeByTimeLine(Long memberId, CursorRequest cursorRequest) {
        /*
            1. TimeLine테이블 조회
            2. 1번에 해당하는 게시물을 조회한다.
         */
        PageCursor<Timeline> pagedTimelines = timelineReadService.getTimelines(memberId, cursorRequest);
        List<Long> postIds = pagedTimelines.body().stream().map(Timeline::getPostId).toList();
        List<Post> posts = postReadService.getPosts(postIds);

        return new PageCursor(pagedTimelines.nextCursorRequest(), posts);
    }
}
