package com.example.fastcampusmysql.application.utils;

public record CursorRequest(
        Long key,
        Long size
) {
    /*
        Auto increment니까 -1인 경우는 없으므로 default값을 -1로
     */
    public static final Long NONE_KEY = -1L;

    public CursorRequest next(Long key) {
        return new CursorRequest(key, size);
    }
}
