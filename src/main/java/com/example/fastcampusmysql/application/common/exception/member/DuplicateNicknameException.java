package com.example.fastcampusmysql.application.common.exception.member;

public class DuplicateNicknameException extends RuntimeException {

    public DuplicateNicknameException(String message) {
        super(message);
    }
}
