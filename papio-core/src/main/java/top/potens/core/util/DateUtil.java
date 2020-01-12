package top.potens.core.util;
import io.lettuce.core.output.KeyStreamingChannel;
import top.potens.core.model.DateScope;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by wenshao on 2019/6/15.
 */
public class DateUtil {
    /**
     * yyyyMMddHHmmssSSS
     */
    public static final DateTimeFormatter FORMATTER_DATETIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    /**
     * yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final DateTimeFormatter FORMATTER_DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    /**
     * yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * 获取当前日期
     *
     * @return
     */
    public static LocalDate getLocalDate() {
        return LocalDate.now();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static LocalTime getLocalTime() {
        return LocalTime.now();
    }

    /**
     * 获取当前日期时间
     *
     * @return
     */
    public static LocalDateTime getLocalDateTime() {
        return LocalDateTime.now();
    }

    /**
     * 获取当前的微秒数
     *
     * @return
     */
    public static long getClockMillis() {
        Clock clock = Clock.systemDefaultZone();
        return clock.millis();
    }

    /**
     * 返回当前时间yyyyMMddHHmmss
     *
     * @return
     */
    public static String getDateTimestamp() {
        return getLocalDateTime().format(FORMATTER_DATETIMESTAMP);
    }

    /**
     * 返回当前时间yyyy-MM-dd
     *
     * @return
     */
    public static String getDate() {
        return getLocalDate().format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * 返回当前系统时间 yyyy-MM-dd HH:mm:ss
     *
     * @return 返回当前系统时间
     */
    public static String getDateTime() {
        return getLocalDateTime().format(FORMATTER_DATETIME);
    }

    /**
     * 获取当月第一天 yyyy-MM-dd
     *
     * @return
     */
    public static String getFirstDayOfMonth() {
        return getLocalDate().withDayOfMonth(1).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * 获取本月最后一天 yyyy-MM-dd
     *
     * @return
     */
    public static String getLastDayOfMonth() {
        LocalDate localDate = getLocalDate();
        return localDate.withDayOfMonth(localDate.lengthOfMonth()).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * 将yyyyMMddHHmmss转为 yyyy-MM-dd HH:mm:ss
     *
     * @param dateTimestamp
     * @return
     */
    public static String formatDateTimestamp(String dateTimestamp) {
        return LocalDateTime.parse(dateTimestamp, FORMATTER_DATETIMESTAMP).format(FORMATTER_DATETIME);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss转为 yyyyMMddHHmmss
     */
    public static String formatDateTime(String dateTime) {
        return parseLocalDateTime(dateTime).format(FORMATTER_DATETIMESTAMP);
    }

    /**
     * 将yyyy-MM-dd HH:mm:ss转为 LocalDateTime
     */
    public static LocalDateTime parseLocalDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, FORMATTER_DATETIME);
    }

    /**
     * 将yyyyMMddHHmmss转为 LocalDateTime
     */
    public static LocalDateTime parseLocalDateTimestamp(String dateTimestamp) {
        return LocalDateTime.parse(dateTimestamp, FORMATTER_DATETIMESTAMP);
    }

    /**
     * yyyy-MM-dd字符串转LocalDate
     *
     * @param dateString
     * @return
     */
    public static LocalDate parseLocalDate(String dateString) {
        return LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * yyyy-MM-dd 增加日期
     *
     * @param date
     * @param days
     * @return
     */
    public static String plusDays(String date, int days) {
        LocalDate localDate = parseLocalDate(date);
        return localDate.plusDays(days).format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param startDate
     *            较小的时间 yyyy-MM-dd
     * @param endDate
     *            较大的时间 yyyy-MM-dd
     * @return 相差天数
     */
    public static int dateCompareTo(String startDate, String endDate) {
        LocalDate startLocalDate = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
        LocalDate endLocalDate = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);
        Period period = Period.between(startLocalDate, endLocalDate);
        return period.getDays();
    }

    /***
     * date to string
     * @param date
     * @return
     */
    public static String getLocalDateStr(Date date) {
        return getLocalDateStr(date, FORMATTER_DATETIME);
    }
    public static String getLocalDateStr(Date date, String formatter) {
        return getLocalDateStr(date, DateTimeFormatter.ofPattern(formatter));
    }
    public static String getLocalDateStr(Date date, DateTimeFormatter dateTimeFormatter) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zoneId);
        return dateTimeFormatter.format(localDateTime);
    }
    public static Date getLocalDateTime(String date, DateTimeFormatter dateTimeFormatter) {
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime parse = LocalDateTime.parse(date, dateTimeFormatter);
        ZonedDateTime zdt = parse.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }
    public static Date getLocalDateTime(String date) {
        return getLocalDateTime(date, FORMATTER_DATETIME);
    }
    public static Date getDateByLocalDate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }

    public static Date getLocalDate(String date, DateTimeFormatter dateTimeFormatter) {
        LocalDate localDate = LocalDate.parse(date, dateTimeFormatter);
        return getDateByLocalDate(localDate);
    }
    public static Date getLocalDate(String date) {
        return getLocalDate(date, FORMATTER_DATE);
    }

    public static void setScopeDate(String scopeDate, KeyStreamingChannel<Date> start, KeyStreamingChannel<Date> end) {
        if (StringUtil.isNotBlank(scopeDate)) {
            String[] split = scopeDate.split(",");
            if (split.length == 1) {
                start.onKey(DateUtil.getLocalDate(split[0]));
            } else if (split.length == 2) {
                start.onKey(DateUtil.getLocalDate(split[0]));
                end.onKey(DateUtil.getLocalDate(split[1]));
            }
        }
    }

    public static DateScope getDateScope(String scopeDate) {
        DateScope dateScope = new DateScope();
        if (StringUtil.isNotBlank(scopeDate)) {
            String[] split = scopeDate.split(",");
            if (split.length == 1) {
                dateScope.setStartDate(DateUtil.getLocalDate(split[0]));
            } else if (split.length == 2) {
                dateScope.setStartDate(DateUtil.getLocalDate(split[0]));
                dateScope.setEndDate(DateUtil.getLocalDate(split[1]));
            }
        }
        return dateScope;
    }
}
