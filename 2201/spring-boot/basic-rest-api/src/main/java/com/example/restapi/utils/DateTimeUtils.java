package com.example.restapi.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import lombok.experimental.UtilityClass;

/**
 * @author dipanjal
 * @since version 0.0.1
 */
@UtilityClass
public class DateTimeUtils {

    public static final String DB_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String API_DATE_FORMAT = "dd-MM-yyyy";
    public static final String API_DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";


    public static String toDBDateFormat(String apiDateStr){
        return convertDateFormat(apiDateStr, API_DATE_FORMAT, DB_DATE_TIME_FORMAT);
    }

    public static String toAPIDateFormat(String dbDateStr){
        return convertDateFormat(dbDateStr, DB_DATE_TIME_FORMAT, API_DATE_FORMAT);
    }

    private static String convertDateFormat(String source, String sourceFormat, String targetFormat){
        LocalDate sourceDate = LocalDate.parse(source, DateTimeFormatter.ofPattern(sourceFormat));
        return sourceDate.format(DateTimeFormatter.ofPattern(targetFormat));
    }

    private static String convertDateTimeFormat(String source, String sourceFormat, String targetFormat){
        LocalDateTime sourceDatTime = LocalDateTime.parse(source, DateTimeFormatter.ofPattern(sourceFormat));
        return sourceDatTime.format(DateTimeFormatter.ofPattern(targetFormat));
    }

    public static String formatDate(Date date, String format) {
        DateFormat dateFormatter = new SimpleDateFormat(format);
        return date == null ? "" : dateFormatter.format(date);
    }

    public static String toAPIDateFormat(Date dbDate){
        String dbDateString = formatDate(dbDate, DB_DATE_TIME_FORMAT);
        return toAPIDateFormat(dbDateString);
    }

    public static String toAPIDateTimeFormat(Date dbDate){
        String dbDateString = formatDate(dbDate, DB_DATE_TIME_FORMAT);
        return convertDateTimeFormat(dbDateString, DB_DATE_TIME_FORMAT, API_DATE_TIME_FORMAT);
    }

    public static Date addHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    public static Date expireAtHour(int hour){
        return addHour(new Date(), hour);
    }

    public static int convertToMilli(int unitValue, int calenderFlag) {
        if(Calendar.HOUR == calenderFlag)
            return 1000 * 60 * 60 * unitValue;
        if(Calendar.MINUTE == calenderFlag)
            return 1000 * 60 * unitValue;
        return 0;
    }
}
