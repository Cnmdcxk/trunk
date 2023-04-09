package netplus.utils.date;

import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    public static List<String> dateFormats = new ArrayList<>();
    static {
        dateFormats.add("yyyyMMdd");
        dateFormats.add("yyyy-MM-dd");
        dateFormats.add("yyyy/MM/dd");
        dateFormats.add("yyyy-MM-dd HH:mm:ss");
        dateFormats.add("yyyy/MM/dd HH:mm:ss");
        dateFormats.add("yyyy/MM/dd HH:mm:ss");
        dateFormats.add("yyyyMMddHHmmss");
    }

    public static Date valueOf(String dateStr, String format){
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            return simpleDateFormat.parse(dateStr);
        }
        catch (ParseException px){
            return null;
        }
        catch (Exception ex){
            return null;
        }
    }

    public static Date valueOf(String dateStr){
        for (String format: dateFormats) {
            Date date = valueOf(dateStr, format);
            if (date != null) {
                return date;
            }
        }
        return null;
    }

    public static String format(Date date, String format) {
    	if (null == date) {
			return null;
		}
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    public static String format(String dateStr, String sourceFormat, String targetFormat){
        if(!StringUtils.isEmpty(dateStr)){
            Date date = valueOf(dateStr,sourceFormat);
            if(!ObjectUtils.isEmpty(date)){
                return DateUtil.format(date,targetFormat);
            }
        }
        return null;
    }

    /**
     * 加年
     */
    public static Date addYear(Date date, Integer year){
        if (null == date) {
            return null;
        }

        if (null == year) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, year);
        return calendar.getTime();
    }

    /**
     * 加月
     */
    public static Date addMonth(Date date, Integer month){
        if (null == date) {
            return null;
        }

        if (null == month) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, month);
        return calendar.getTime();
    }


    /**
     * 获取某个日期时间差的日期
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, Integer days){
        if (null == date) {
            return null;
        }

        if (null == days) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 获取某个日期时间差的日期
     * @param date
     * @return
     */
    public static Date addHours(Date date, Integer hours){
        if (null == date) {
            return null;
        }

        if (null == hours) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }


    /**
     * 获取某个日期时间差的日期
     * @param date
     * @return
     */
    public static Date addSeconds(Date date, Integer seconds){
        if (null == date) {
            return null;
        }

        if (null == seconds) {
            return date;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }


    public static String getFirstDayOfMonth(Date d, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        //获取某月最小天数
        int firstDay = cal.getMinimum(Calendar.DATE);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH,firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(cal.getTime());
    }

    public static String getLastDayOfMonth(Date d, String format) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);

        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DATE);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(cal.getTime());
    }

    public static boolean isDateStr (String v) {
        Date d = valueOf(v);
        return null != d;
    }


    public static String getTimeStampStr () {
        return getTimeStampStr(new Date());
    }


    public static String getTimeStampStr (Date date) {
        return String.valueOf(date.getTime());
    }

    public static Timestamp curTimestamp() {
        return new Timestamp((new Date()).getTime());
    }
}
