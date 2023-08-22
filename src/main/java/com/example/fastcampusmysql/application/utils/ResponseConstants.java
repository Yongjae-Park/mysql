package com.example.fastcampusmysql.application.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseConstants {

    public static final ResponseEntity<String> DUPLICATED_EMAIL =
            new ResponseEntity<>("중복된 이메일입니다.", HttpStatus.CONFLICT);

    public static final ResponseEntity<String> DUPLICATED_NICKNAME =
            new ResponseEntity<>("중복된 닉네임입니다.", HttpStatus.CONFLICT);
}
