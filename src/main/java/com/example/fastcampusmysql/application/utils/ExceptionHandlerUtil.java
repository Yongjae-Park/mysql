package com.example.fastcampusmysql.application.utils;


import com.example.fastcampusmysql.application.common.exception.ErrorResponse;

public class ExceptionHandlerUtil {

    public static ErrorResponse createError(String message) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(message)
                .build();
        return errorResponse;
    }
}
