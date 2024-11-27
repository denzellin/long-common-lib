package com.isylph.utils.datetime;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtils {

    private static final ZoneId zone = ZoneId.systemDefault();


    /**
     * 注意在表示年的Y
     * jdk7和8，甚至jdk9(https://docs.oracle.com/javase/9/docs/api/java/text/SimpleDateFormat.html)，除了“y”，引入了"Y"，“Y”表示Week year，
     */
    private static final String FORMATPATTERN_DATE = "yyyy-MM-dd";

    private static final String FORMATPATTERN_SHORT_DATE = "yyyyMMdd";

    private static final String FORMATPATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";



    public static Long getTimestamp() {
        return System.currentTimeMillis();
    }


    public static Date localDateTimeToDate(LocalDateTime ldt){
        return Date.from(ldt.atZone( ZoneId.systemDefault()).toInstant());
    }


    public static Date localDateToDate(LocalDate ldt){
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = ldt.atStartOfDay(zoneId);

        return Date.from(zdt.toInstant());
    }


    /**
     * 将long类型的timestamp转为LocalDateTime
     * @param timestamp
     * @return
     */
    public static LocalDateTime getDateTimeOfTimestamp(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }

    /**
     * 将LocalDateTime转为long类型的timestamp
     * @param localDateTime
     * @return
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }

    // Date > java.time.LocalDateTime
    public static LocalDateTime DateToLocalDateTime(Date date) {
        if(null == date){
            return LocalDateTime.now();
        }
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant, zone);
    }


    //Date > java.time.LocalDate
    public static LocalDate DateToLocalDate(Date date) {
        if(null == date){
            return LocalDate.now();
        }
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalDate();
    }

    //Date > java.time.LocalTime
    public static LocalTime DateToLocalTime(Date date) {
        if(null == date){
            date = new Date();
        }
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime.toLocalTime();
    }



    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        DateTimeFormatter sdf_date = DateTimeFormatter.ofPattern(FORMATPATTERN_DATE);
        return LocalDate.now().format(sdf_date);
    }


    /**
     * 根据时间，获取月初的日期字符, 格式：yyyy-MM-dd 00:00:00
     *
     * @return
     */
    public static String getFirstDayOfMonth() {
        LocalDate ld = LocalDate.now();
        ld = LocalDate.of(ld.getYear(), ld.getMonth(), 0);
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern(FORMATPATTERN_DATE);
        return ld.format(sdf) + " 00:00:00" ;
    }

    /**
     * 根据时间的字符格式：yyyy-MM-dd 00:00:00
     *
     * @return
     */
    public static String getDateTimeString(LocalDateTime dt) {
        if (null == dt){
            return "";
        }

        DateTimeFormatter sdf = DateTimeFormatter.ofPattern(FORMATPATTERN_DATETIME);
        return dt.format(sdf) ;
    }

    /**
     * 根据时间，获取月初的日期字符, 格式：yyyy-MM-dd 00:00:00
     *
     * @return
     */
    public static LocalDateTime getFirstDayDatetimeOfMonth() {
        LocalDate ld = LocalDate.now();
        ld = LocalDate.of(ld.getYear(), ld.getMonth(), 1);
        return LocalDateTime.of(ld, LocalTime.of(0, 0,0, 0)) ;
    }

    /**
     * 根据时间，获取周一的日期
     */
    public static LocalDateTime getMondayDatetime() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(DayOfWeek.MONDAY);
    }
    /**
     * 根据时间，获取周一的日期
     */
    public static String getMondayDatetimeString() {
        LocalDateTime nowMonday = LocalDateTime.of(LocalDate.now(), LocalTime.MIN).with(DayOfWeek.MONDAY);
        return getDateTimeString(nowMonday);
    }

    public static LocalDateTime parseDateTime(String time, String fmt){
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern(fmt);
        return LocalDateTime.parse(time, sdf) ;
    }


    public static LocalDate parseDate(String time, String fmt){
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern(fmt);
        return LocalDate.parse(time, sdf) ;
    }

}
