package com.example.fastcampusmysql.application.usecase;

import com.example.fastcampusmysql.domain.follow.service.FollowWriteService;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import com.example.fastcampusmysql.domain.member.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CreateFollowMemberUsecase {
    /*
        도메인 서비스의 흐름을 제어하는 역할
        도메인간 결합도를 낮추기 위해
        팔로우 도메인 서비스에서 멤버관련 서비스를 주입받는 일 없도록 이 usecase에서 흐름제어

     */

    final private MemberReadService memberReadService;
    final private FollowWriteService followWriteService;
    public void execute(Long fromMemberId, Long toMemberId) {
        /*
            1. 입력받은 memberId로 회원조회
            2. FollowWriteService.create()
         */

        MemberDto fromMember = memberReadService.getMember(fromMemberId);
        MemberDto toMember = memberReadService.getMember(toMemberId);

        followWriteService.create(fromMember, toMember);


    }
}
