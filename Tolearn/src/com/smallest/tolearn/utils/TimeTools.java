
package com.smallest.tolearn.utils;

import java.util.Calendar;
import java.util.TimeZone;

public class TimeTools {

    public static final String TAG = "TimeTools";
    public static final String STATE_MORNING = "morning";
    public static final String STATE_AFTERNOON = "afternoon";
    public static final String STATE_EVENING = "evening";
    public final static String[] TIME_AREAS = {
            "00:00-00:59", "01:00-01:59", "02:00-02:59", "03:00-03:59",
            "004:00-05:59", "05:00-05:59", "06:00-06:59", "07:00-07:59", "08:00-08:59",
            "09:00-09:59", "10:00-10:59",
            "11:00-11:59", "12:00-12:59", "13:00-13:59", "14:00-14:59", "15:00-15:59",
            "16:00-16:59", "17:00-17:59",
            "18:00-18:59", "19:00-19:59", "20:00-20:59", "21:00-21:59", "22:00-22:59",
            "23:00-23:59"
    };

    public static String getTime24Style() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+08"));
        String year = to00Style(cal.get(Calendar.YEAR));
        String month = to00Style(cal.get(Calendar.MONTH) + 1);
        String day = to00Style(cal.get(Calendar.DATE));
        String hour = to00Style(cal.get(Calendar.HOUR_OF_DAY));
        String min = to00Style(cal.get(Calendar.MINUTE));
        return year + "-" + String.valueOf(month) + "-" + String.valueOf(day) + " "
                + String.valueOf(hour) + ":"
                + String.valueOf(min);
    }

    public static int getCurrentYear() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+08"));
        return cal.get(Calendar.YEAR);
    }

    public static int getCurrentMonth() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+08"));
        return cal.get(Calendar.MONTH);
    }

    public static int getCurrentDay() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+08"));
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    public static int getCurrentHour() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+08"));
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static int getCurrentDayOfWeek() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+08"));
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static int getCurrentMinute() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+08"));
        return cal.get(Calendar.MINUTE);
    }

    public static String getTime24Style(String milliseconds) {
        try {
            long millis = Long.parseLong(milliseconds);
            return getTime24Style(millis);
        } catch (Exception e) {
            return "";
        }
    }

    public static String getTime24Style(long milliseconds) {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+08"));
        cal.setTimeInMillis(milliseconds);
        String year = to00Style(cal.get(Calendar.YEAR));
        String month = to00Style(cal.get(Calendar.MONTH) + 1);
        String day = to00Style(cal.get(Calendar.DATE));
        String hour = to00Style(cal.get(Calendar.HOUR_OF_DAY));
        String min = to00Style(cal.get(Calendar.MINUTE));
        return String.valueOf(month) + "-" + String.valueOf(day) + " " + String.valueOf(hour) + ":"
                + String.valueOf(min);
    }

    /**
     * am 06:00 - am 10:00 am 10:00 - pm 17:00 pm 17:00 - am 06:00
     */
    public static String getTimeState() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+08"));
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (hour >= 6 && hour <= 10) {
            return STATE_MORNING;
        } else if (hour > 10 && hour < 17) {
            return STATE_AFTERNOON;
        } else {
            return STATE_EVENING;
        }
    }

    public static String to00Style(int n) {
        if (n < 0) {
            return "";
        } else if (n < 10) {
            return String.valueOf("0" + n);
        } else {
            return String.valueOf(n);
        }
    }

    public static String getCurrentTimeArea() {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT+08"));
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        return TIME_AREAS[hour];
    }
}
