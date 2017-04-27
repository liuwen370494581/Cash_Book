package star.liuwen.com.cash_books.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by liuwen on 2016/12/15.
 * 日期转化类
 */
public class DateTimeUtil {

    /**
     * 阿里巴巴java语句推荐使用的DateTimeUtil
     */
    private static final ThreadLocal<DateFormat> df = new ThreadLocal<DateFormat>() {
        @Override
        protected DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };


    //获取相应的年和月 分割的
    public static String[] getShowCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        String[] msdf = sdf.format(new Date()).split("-");
        return msdf;
    }

    //获取相应的年和月 分割的
    public static String getShowCurrentTime(String data) {
        String[] msdf = data.split("-");
        String sdf = msdf[3] + ":" + msdf[4] + ":" + msdf[5];
        return sdf;
    }


    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }

    public static String getCurrentTimeMinSec() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }

    public static String getCurrentTimeHourMIn(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(date);
    }

    public static String getCurrentMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM月");
        return sdf.format(new Date());
    }

    public static String getCurrentYearMonth() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return sdf.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime_Today() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        return sdf.format(new Date());
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }

    public static String getYear(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy");
        return format.format(date);
    }

    public static String getYearMonthDay_(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getYearMonthDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }


    public static long LoadDay(String a1, String b1) throws ParseException {

        Date A1 = new SimpleDateFormat("yyyy-MM-dd").parse(a1);
        Date B1 = new SimpleDateFormat("yyyy-MM-dd").parse(b1);
        return (B1.getTime() - A1.getTime()) / (24 * 60 * 60 * 1000);
    }

    //将string转化成date
    public static Date getStringDayToDate(String date) {
        Date date2 = null;

        try {
            SimpleDateFormat d2 = new SimpleDateFormat("yyyy-MM-dd");
            date2 = d2.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date2;
    }


}

