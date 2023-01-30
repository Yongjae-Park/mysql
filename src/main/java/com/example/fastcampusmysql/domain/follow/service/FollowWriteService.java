package com.example.fastcampusmysql.domain.follow.service;

import com.example.fastcampusmysql.domain.follow.entity.Follow;
import com.example.fastcampusmysql.domain.follow.repository.FollowRepository;
import com.example.fastcampusmysql.domain.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class FollowWriteService {

    final private FollowRepository followRepository;

    public void create(MemberDto fromMember, MemberDto toMember) {
        /*
            from, to 회원 정보를 받아서 저장
            from <-> to validate 추가 (같으면 안됨)
            1. create(Long fromMemberId, Long toMemberId) 로 받아오면 해당 from,to memberId가 존재하는지 validation을 여기서 해야된다.
                그러면 도메인 간 결합도가 생기게 된다.
                -> memberDto를 받아온다.
            서로 다른 도메인의 데이터를 주고받아야 할때 서로 다른 도메인의 흐름을 제어해야할 때 어디에서 해야할지 고민
            경계간 통신에 대한 고민
         */

        Assert.isTrue(fromMember.id().equals(toMember.id()), "from, to 회원이 동일합니다.");

        Follow follow = Follow.builder()
                .fromMemberId(fromMember.id())
                .toMemberId(toMember.id())
                .build();

        followRepository.save(follow);
    }
}
