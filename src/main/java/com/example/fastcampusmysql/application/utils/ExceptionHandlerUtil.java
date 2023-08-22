package com.example.fastcampusmysql.application.utils;


import com.example.fastcampusmysql.application.common.exception.ErrorResponse;

import java.time.LocalDateTime;

public class ExceptionHandlerUtil {

    public static ErrorResponse createError(LocalDateTime timestamp, String message, String trace) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(timestamp)
                .message(message)
                .trace(trace)
                .build();

        return errorResponse;
    }
}
