package nz.co.udenbrothers.clockwork.util;

import android.text.format.Time;

/**
 *
 */

public class MyDateUtils {
    public static boolean isThisMonth(long when) {
        Time time = new Time();
        time.set(when);

        int thenYear = time.year;
        int thenMonth = time.month;

        time.set(System.currentTimeMillis());
        return (thenYear == time.year)
                && (thenMonth == time.month);
    }
    public static boolean isThisWeek(long when) {
        Time time = new Time();
        time.set(when);

        int thenYear = time.year;
        int week = time.getWeekNumber();

        time.set(System.currentTimeMillis());
        return (thenYear == time.year)
                && (week == time.getWeekNumber());
    }

}
