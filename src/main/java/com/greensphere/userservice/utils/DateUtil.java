package com.greensphere.userservice.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Component
public class DateUtil {

    private DateUtil(){}

    public LocalDateTime getStartOfTheDay() {
        LocalDate now = LocalDate.now();
        return now.atStartOfDay();
    }

    public LocalDateTime getEndOfTheDay() {
        LocalDate now = LocalDate.now();
        return LocalDateTime.of(now, LocalTime.MAX);
    }


    public static String getDateTime(LocalDateTime dateTime) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");
        return dateTime.format(dateFormat);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd,yyyy 'at' hh.mma");
        return dateTime.format(dateFormatter).toLowerCase();
    }

    public static String formatLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy");
        return dateTime.format(formatter);
    }

    public static LocalDateTime convertDateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
