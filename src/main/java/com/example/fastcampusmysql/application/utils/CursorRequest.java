package com.example.fastcampusmysql.application.utils;

import com.example.fastcampusmysql.domain.post.entity.Post;

import java.util.List;

public record CursorRequest(
        Long key,
        int size
) {
    /*
        Auto increment니까 -1인 경우는 없으므로 default값을 -1로
     */
    public static final Long NONE_KEY = -1L;

    public CursorRequest next(long nextKey) {
        return new CursorRequest(nextKey, size);
    }

    /**
     * hasKey()체크 메소드가 서비스에 있지 않고 유틸로 빼는 이유
     * 클라이언트에서 시작값을 주는 것으로 바뀌게 될 경우 서비스마다 코드를 모두 변경해야 하므로 유틸로 제네릭하게 관리하는 것이 합리적
     * @return
     */
    public Boolean hasKey() {
        return key != null;
    }

    public CursorRequest next(Long key) {
        return new CursorRequest(key, size);
    }
}
