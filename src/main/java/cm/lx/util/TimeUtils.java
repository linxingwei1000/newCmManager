package cm.lx.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TimeUtils {

    public static final String FORMAT_ONE = "yyyy-MM-dd";

    public static final String FORMAT_TWO = "yyyy-MM-dd HH:mm:ss";


    public static long transformDateToTimetag(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String transformTimetagToDate(Long time, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        try {
            return simpleDateFormat.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer dayBetweenTwoTime(Long begin, Long end) {
        if (begin == null) begin = System.currentTimeMillis();
        return (int) ((end - begin) / (1000 * 60 * 60 * 24));
    }

}
