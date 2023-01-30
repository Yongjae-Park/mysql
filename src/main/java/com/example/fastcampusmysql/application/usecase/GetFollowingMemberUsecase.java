package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.dto.FollowDto;
import com.example.fastcampusmysql.domain.follow.repository.FollowRepository;
import com.example.fastcampusmysql.domain.follow.service.FollowReadService;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetFollowingMemberUsecase {

    final private MemberReadService memberReadService;

    final private FollowReadService followReadService;
    public List<MemberDto> execute(Long memberId) {
        /*
            1. fromMemberID = memberId -> follow list
            2. 1을 순회하면서 회원정보를 찾으면 된다.
         */
        List<FollowDto> followings = followReadService.getFollowings(memberId);
        List<Long> followingMemberIds = followings
                .stream()
                .map(FollowDto::toMemberId)
                .toList();

        return memberReadService.getMembers(followingMemberIds);
    }
}
