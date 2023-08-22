package com.example.fastcampusmysql.application.common.exception;

import com.example.fastcampusmysql.application.common.exception.member.DuplicateEmailException;
import com.example.fastcampusmysql.application.common.exception.member.DuplicateNicknameException;
import com.example.fastcampusmysql.application.utils.ExceptionHandlerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public final ResponseEntity<ErrorResponse> DuplicateEmailExceptionHandler(DuplicateEmailException exception) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = ExceptionHandlerUtil.createError(LocalDateTime.now(), message, exception.getStackTrace().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DuplicateNicknameException.class)
    public final ResponseEntity<ErrorResponse> DuplicationNicknameExceptionHandler(DuplicateNicknameException exception) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = ExceptionHandlerUtil.createError(LocalDateTime.now(), message, exception.getStackTrace().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorResponse> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
        String message = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        ErrorResponse errorResponse = ExceptionHandlerUtil.createError(LocalDateTime.now(), message, exception.getStackTrace().toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
