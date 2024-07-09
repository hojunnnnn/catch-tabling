package com.catchtabling.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LocalDateTimeUtil {

    private LocalDateTimeUtil() {}

    public static LocalDate stringToLocalDate(String strDate) {
        return strDate == null ? null : LocalDate.parse(strDate, DateTimeFormatter.ISO_DATE);
    }

    public static LocalDateTime stringToLocalDateTime(String strDate) {
        return strDate == null ? null : LocalDateTime.parse(strDate, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public static String localDateToString(LocalDate localDate) {
        return localDate == null ? null : localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static String localDateTimeToString(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

}
