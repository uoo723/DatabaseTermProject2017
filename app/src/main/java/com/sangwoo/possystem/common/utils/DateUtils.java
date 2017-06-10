package com.sangwoo.possystem.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils {

    public static String convertToBirthString(Date date) {
        DateFormat transFormat = new SimpleDateFormat("MMdd");
        return transFormat.format(date);
    }

    public static Date convertToBirthDate(String dateStr) throws Exception {
        DateFormat transFormat = new SimpleDateFormat("MMdd");
        return transFormat.parse(dateStr);
    }

    public static String convertToString(Date date) {
        DateFormat transFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        return transFormat.format(date);
    }

    public static Date convertBeginOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date convertEndOfDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }
}
