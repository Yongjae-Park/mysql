package com.example.fastcampusmysql.application.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FieldUtils {
    public static LocalDate getLocalDate(LocalDate date) {
        if (date == null)
            return LocalDate.now();
        return date;
    }

    public static LocalDateTime getLocalDateTime(LocalDateTime dateTime) {
        if (dateTime == null)
            return LocalDateTime.now();
        return dateTime;
    }
}
