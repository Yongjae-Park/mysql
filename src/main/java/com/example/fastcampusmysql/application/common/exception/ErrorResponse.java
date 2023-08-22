package com.example.fastcampusmysql.application.common.exception;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorResponse {
    private String message;

    private LocalDateTime timestamp;

    private String trace;
}