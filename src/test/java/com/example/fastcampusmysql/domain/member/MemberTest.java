package com.example.fastcampusmysql.domain.member;

import com.example.fastcampusmysql.domain.member.entity.Member;
import com.example.fastcampusmysql.util.MemberFixtureFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

public class MemberTest {

    @DisplayName("회원은 닉네임을 변경할 수 있다.")
    @Test
    public void testChangeName() {

//        LongStream.range(0, 10)
//                .mapToObj(i -> MemberFixtureFactory.create(i))
//                .forEach(member -> {
//                    System.out.println(member.getNickname());
//                });

        Member member = MemberFixtureFactory.create();
        String expected = "ytothej92";

        member.changeNickname(expected);

        assertEquals(expected, member.getNickname());
    }

    @DisplayName("회원의 닉네임은 10자를 초과할 수 없다.")
    @Test
    public void testNicknameMaxLength() {

        Member member = MemberFixtureFactory.create();
        String overMaxLengthNickName = "ytothej92ytothej92";

        assertThrows(IllegalArgumentException.class,
                () -> member.changeNickname(overMaxLengthNickName));
    }

}
