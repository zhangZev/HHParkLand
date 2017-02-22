/* 
 * 文件名：ToolsDate.java
 * 版权：Copyright 2009-2010 KOOLSEE MediaNet. Co. Ltd. All Rights Reserved. 
 * 描述： 
 * 修改人：
 * 修改时间：
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */
package com.benefit.buy.library.utils.tools;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.annotation.SuppressLint;

/**
 * 〈一句话功能简述〉 〈功能详细描述〉
 * @author qyl
 * @version HDMNV100R001, 2013-6-23
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@SuppressLint("SimpleDateFormat")
public class ToolsDate {

    private final static String APPLICATION_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final static String APPLICATION_DATE_FORMATTIME = "HH:mm";

    private final static String APPLICATION_DATE_FORMATMONTHDAY = "MM月dd日";

    private final static String APPLICATION_DATE_FORMAT_DAY = "yyyy/MM/dd";

    public static long clientServerTimeDiff = 0;

    public static void initTimeDiff(long time) {
        clientServerTimeDiff = time;
    }

    /**
     * 根据字符串获取时间的格林时间
     * @param dateTime
     * @return
     */
    public static long getDayTimeByString(String dateTime) {
        SimpleDateFormat sdf = new SimpleDateFormat(APPLICATION_DATE_FORMAT);
        try {
            return sdf.parse(dateTime).getTime();
        }
        catch (ParseException e) {
            return 0;
        }
    }

    /**
     * 获取当前时间
     * @return long int
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis() + clientServerTimeDiff;
    }

    /**
     * 计算小时 〈一句话功能简述〉 〈功能详细描述〉
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static long timeDay(String date) {
        try {
            Date endDate = parseHorDate(date);
            Date startDate = getFormatCurrentTimeToDate();
            long temp = endDate.getTime() - startDate.getTime(); //相差毫秒数
            long hours = temp / 1000 / 3600; //相差小时数
            return hours;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 返回小时 〈一句话功能简述〉 〈功能详细描述〉
     * @param dateStr 字符
     * @param formatString 格式
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static int houseFormat(String dateStr, String formatString) {
        if (ToolsKit.isEmpty(dateStr)) {
            return 0;
        }
        SimpleDateFormat originalSdf = new SimpleDateFormat(formatString);
        SimpleDateFormat normalSdf = new SimpleDateFormat("HH");
        Date date = null;
        try {
            date = originalSdf.parse(dateStr);
            String dateHouse = normalSdf.format(date);
            return Integer.parseInt(dateHouse);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 将从网关获取的yyyy-MM-dd HH:mm:ss时间字符串转换为HH:mm
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String formatNorDate(String dateStr) {
        if (ToolsKit.isEmpty(dateStr) || "0".equals(dateStr)) {
            return "";
        }
        if (dateStr.length() != 19) {
            System.out.println(dateStr);
        }
        SimpleDateFormat originalSdf = new SimpleDateFormat(APPLICATION_DATE_FORMAT);
        SimpleDateFormat normalSdf = new SimpleDateFormat("HH:mm");
        Date date = null;
        try {
            date = originalSdf.parse(dateStr);
            return normalSdf.format(date);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static long dateDay(String startTime) {
        if (ToolsKit.isEmpty(startTime)) {
            return dateDay(dateFormatNormal(new Date()), dateFormatNormal(getFormatCurrentTimeToDate()));
        }
        else {
            return dateDay(startTime, dateFormatNormal(getFormatCurrentTimeToDate()));
        }
    }

    /**
     * 〈一句话功能简述〉 〈功能详细描述〉
     * @param startTime 必须是 yyyy-MM-dd HH:mm:ss
     * @param endTime 必须是 yyyy-MM-dd HH:mm:ss
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static long dateDay(String startTime, String endTime) {
        //按照传入的格式生成一个simpledateformate对象
        if (ToolsKit.isEmpty(startTime) || "0".equals(startTime)) {
            startTime = "0000-00-00 00:00:00";
        }
        if (ToolsKit.isEmpty(endTime) || "0".equals(endTime)) {
            endTime = "0000-00-00 00:00:00";
        }
        SimpleDateFormat sd = new SimpleDateFormat(APPLICATION_DATE_FORMAT);
        long ns = 1000;//一秒钟的毫秒数
        long min = 0;
        try {
            //获得两个时间的毫秒时间差异
            long start = sd.parse(startTime).getTime();
            long end = sd.parse(endTime).getTime();
            long diff = end - start;
            min = diff / ns;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return min;
    }

    /**
     * 时间转换为指定时间格式
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String dateFormatNormal(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat originalSdf = new SimpleDateFormat(APPLICATION_DATE_FORMAT);
        return originalSdf.format(date);
    }

    /**
     * 时间转换为指定时间格式
     * @param date
     * @return MM月DD日
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String dateFormatNormalMonthDay(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat originalSdf = new SimpleDateFormat(APPLICATION_DATE_FORMATMONTHDAY);
        return originalSdf.format(date);
    }

    /**
     * 时间转换为指定时间格式
     * @param date
     * @return HH:MM
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String dateFormatNormalTime(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat originalSdf = new SimpleDateFormat(APPLICATION_DATE_FORMATTIME);
        return originalSdf.format(date);
    }

    /**
     * 时间转换为指定时间格式
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String dateFormatDay(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat originalSdf = new SimpleDateFormat(APPLICATION_DATE_FORMAT_DAY);
        return originalSdf.format(date);
    }

    public static Date getFormatCurrentTimeToDate() {
        return new Date(getCurrentTime());
    }

    /**
     * 天数差 〈一句话功能简述〉 〈功能详细描述〉
     * @param startDateTime
     * @param endDateTime
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static long dayFromAnnoum(String startDateTime, String endDateTime) {
        Date startDate = null;
        Date endDate = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            startDate = format.parse(startDateTime);
            endDate = format.parse(endDateTime);
            long startTimeStamp = startDate.getTime();
            long endTimeStamp = endDate.getTime();
            long seconds = (endTimeStamp - startTimeStamp) / 1000;
            long minutes = Math.abs(seconds / 60);
            long hours = Math.abs(minutes / 60);
            long days = Math.abs(hours / 24);
            return days;
        }
        catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String timeAgo(String timeStr) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
            date = format.parse(timeStr);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        long timeStamp = date.getTime();
        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();
        long seconds = (currentTimeStamp - timeStamp) / 1000;
        long minutes = Math.abs(seconds / 60);
        long hours = Math.abs(minutes / 60);
        long days = Math.abs(hours / 24);
        if (seconds <= 15) {
            return "刚刚";
        }
        else if (seconds < 60) {
            return seconds + "秒前";
        }
        else if (seconds < 120) {
            return "1分钟前";
        }
        else if (minutes < 60) {
            return minutes + "分钟前";
        }
        else if (minutes < 120) {
            return "1小时前";
        }
        else if (hours < 24) {
            return hours + "小时前";
        }
        else if (hours < (24 * 2)) {
            return "1天前";
        }
        else if (days < 30) {
            return days + "天前";
        }
        else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
            String dateString = formatter.format(date);
            return dateString;
        }
    }

    public static String timeLeft(String timeStr) {
        Date date = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss Z");
            date = format.parse(timeStr);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
        long timeStamp = date.getTime();
        Date currentTime = new Date();
        long currentTimeStamp = currentTime.getTime();
        long total_seconds = (timeStamp - currentTimeStamp) / 1000;
        if (total_seconds <= 0) {
            return "";
        }
        long days = Math.abs(total_seconds / (24 * 60 * 60));
        long hours = Math.abs((total_seconds - (days * 24 * 60 * 60)) / (60 * 60));
        long minutes = Math.abs((total_seconds - (days * 24 * 60 * 60) - (hours * 60 * 60)) / 60);
        long seconds = Math.abs((total_seconds - (days * 24 * 60 * 60) - (hours * 60 * 60) - (minutes * 60)));
        String leftTime;
        if (days > 0) {
            leftTime = days + "天" + hours + "小时" + minutes + "分" + seconds + "秒";
        }
        else if (hours > 0) {
            leftTime = hours + "小时" + minutes + "分" + seconds + "秒";
        }
        else if (minutes > 0) {
            leftTime = minutes + "分" + seconds + "秒";
        }
        else if (seconds > 0) {
            leftTime = seconds + "秒";
        }
        else {
            leftTime = "0秒";
        }
        return leftTime;
    }

    /**
     * 将毫秒转成时间
     * @param l
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String getTimeMillisToDate(long l) {
        Timestamp d = new Timestamp(l);
        return d.toString().substring(0, 19);
    }

    /**
     * 当前时间
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static Timestamp crunttime() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 获取当前时间的字符串
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String getCurrentDate() {
        Timestamp d = crunttime();
        return d.toString().substring(0, 10);
    }

    /**
     * 加小时 〈一句话功能简述〉 〈功能详细描述〉
     * @param dataStr
     * @param hourse
     * @param formatString
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String addHourse(String dataStr, int hourse, String formatString) {
        SimpleDateFormat sdf = new SimpleDateFormat(formatString);
        Date date = null;
        try {
            date = sdf.parse(dataStr);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        ca.add(Calendar.HOUR_OF_DAY, hourse);
        return sdf.format(ca.getTime());
    }

    /**
     * 获取当前时间的字符串
     * @return 2006-07-07 22:10:10
     * @see [类、类#方法、类 #成员]
     * @since [产品/模块版本]
     */
    public static String getCurrentDateTime() {
        Timestamp d = crunttime();
        return d.toString().substring(0, 19);
    }

    @SuppressLint("SimpleDateFormat")
    public static String getWeekDay() {
        Calendar date = Calendar.getInstance();
        date.setTime(crunttime());
        return new SimpleDateFormat("EEEE").format(date.getTime());
    }

    /**
     * 获取指定时间的字符串,只到日期 〈一句话功能简述〉 〈功能详细描述〉
     * @param t
     * @return 2006-07-07
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String getStrDate(Timestamp t) {
        return t.toString().substring(0, 10);
    }

    /**
     * 获取指定时间的字符串
     * @param t
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String getStrDateTime(Timestamp t) {
        return t.toString().substring(0, 19);
    }

    /**
     * 获得当前日期的前段日期
     * @param days
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String getStrIntervalDate(String days) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -Integer.parseInt(days));
        String strBeforeDays = sdf.format(cal.getTime());
        return strBeforeDays;
    }

    /**
     * 格式化时间
     * @param dt
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static Date parseDateTime(String dt) {
        Date jDt = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (dt.length() > 10) {
                jDt = sdf.parse(dt);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return jDt;
    }

    /**
     * 格式化时间yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String parseDateTime(Date date) {
        String s = null;
        if (date != null) {
            try {
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                s = f.format(date);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return s;
    }

    /**
     * 格式化时间 〈一句话功能简述〉 〈功能详细描述〉
     * @param date
     * @param formatString
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String parseDate(Date date, String formatString) {
        String s = null;
        if (date != null) {
            try {
                SimpleDateFormat f = new SimpleDateFormat(formatString);
                s = f.format(date);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        return s;
    }

    /**
     * 将指定字符串转换为时间 〈一句话功能简述〉 〈功能详细描述〉
     * @param dt
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static Date parseHorDate(String dt) {
        Date jDt = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");
            jDt = sdf.parse(dt);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return jDt;
    }

    /**
     * 格式化日期
     * @param dt yyyy-MM-dd
     * @return yyyy-MM-dd
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static Date parseDate(String dt) {
        Date jDt = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (dt.length() >= 8) {
                jDt = sdf.parse(dt);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return jDt;
    }

    /**
     * 格式化时间yyyy-MM-dd
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String parseDate(Date date) {
        String s = null;
        try {
            if (date != null) {
                SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
                s = f.format(date);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /****
     * 将指定日期加上时分秒
     * @param dt
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String getLongDateFromShortDate(String dt) {
        String strDT = dt;
        try {
            if ((strDT != null) && (strDT.length() <= 10)) {
                strDT = dt.trim() + " 00:00:00";
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return strDT;
    }

    /**
     * 将指定日期转会为年月日时分
     * @param dt
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String getShortDateToHHMM(String dt) {
        String jDt = dt;
        try {
            if ((jDt != null) && (jDt.length() <= 10)) {
                jDt = jDt + " 00:00";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            jDt = sdf.parse(jDt).toLocaleString();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return jDt;
    }

    /**
     * 对指定时间字符串进行截取
     * @param dateStr
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String formatDateToHHMM(String dateStr) {
        String resultDate = null;
        try {
            if (dateStr.length() > 10) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss");
                Date date = sdf.parse(dateStr);
                resultDate = sdf.format(date);
            }
            else {
                resultDate = dateStr;
            }
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return resultDate;
    }

    /**
     * 返回日期 格式:2006-07-05
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static Timestamp date(String str) {
        Timestamp tp = null;
        if (str.length() <= 10) {
            String[] string = str.trim().split("-");
            int one = Integer.parseInt(string[0]) - 1900;
            int two = Integer.parseInt(string[1]) - 1;
            int three = Integer.parseInt(string[2]);
            tp = new Timestamp(one, two, three, 0, 0, 0, 0);
        }
        return tp;
    }

    /**
     * 获取指定日期之后的日期字符串 如 2007-04-15 后一天 就是 2007-04-16
     * @param strDate
     * @param day
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String getNextDay(String strDate, int day) {
        if ((strDate != null) && !strDate.equals("")) {
            Calendar cal1 = Calendar.getInstance();
            String[] string = strDate.trim().split("-");
            int one = Integer.parseInt(string[0]) - 1900;
            int two = Integer.parseInt(string[1]) - 1;
            int three = Integer.parseInt(string[2]);
            cal1.setTime(new Date(one, two, three));
            cal1.add(Calendar.DAY_OF_MONTH, day);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            return formatter.format(cal1.getTime());
        }
        else {
            return null;
        }
    }

    /**
     * 获取指定日期之后的日期字符串 如 2007-02-28 后一年 就是 2008-02-29 （含闰年）
     * @param strDate
     * @param year
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String getNextYear(String strDate, int year) {
        Calendar cal1 = Calendar.getInstance();
        String[] string = strDate.trim().split("-");
        int one = Integer.parseInt(string[0]) - 1900;
        int two = Integer.parseInt(string[1]) - 1;
        int three = Integer.parseInt(string[2]);
        cal1.setTime(new Date(one, two, three));
        cal1.add(Calendar.YEAR, year);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(cal1.getTime());
    }

    /**
     * 返回时间和日期 格式:2006-07-05 22:10:10
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static Timestamp datetime(String str) {
        Timestamp tp = null;
        if ((str != null) && (str.length() > 10)) {
            String[] string = str.trim().split(" ");
            String[] date = string[0].split("-");
            String[] time = string[1].split(":");
            int date1 = Integer.parseInt(date[0]) - 1900;
            int date2 = Integer.parseInt(date[1]) - 1;
            int date3 = Integer.parseInt(date[2]);
            int time1 = Integer.parseInt(time[0]);
            int time2 = Integer.parseInt(time[1]);
            int time3 = Integer.parseInt(time[2]);
            tp = new Timestamp(date1, date2, date3, time1, time2, time3, 0);
        }
        return tp;
    }

    /**
     * 返回日期和时间(没有秒) 格式:2006-07-05 22:10
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static Timestamp datetimeHm(String str) {
        Timestamp tp = null;
        if (str.length() > 10) {
            String[] string = str.trim().split(" ");
            String[] date = string[0].split("-");
            String[] time = string[1].split(":");
            int date1 = Integer.parseInt(date[0]) - 1900;
            int date2 = Integer.parseInt(date[1]) - 1;
            int date3 = Integer.parseInt(date[2]);
            int time1 = Integer.parseInt(time[0]);
            int time2 = Integer.parseInt(time[1]);
            tp = new Timestamp(date1, date2, date3, time1, time2, 0, 0);
        }
        return tp;
    }

    /**
     * 获得当前系统日期与本周一相差的天数
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    private static int getMondayPlus() {
        Calendar calendar = Calendar.getInstance();
        // 获得今天是一周的第几天，正常顺序是星期日是第一天，星期一是第二天......
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 星期日是第一天
        return (dayOfWeek == 1) ? -6 : 2 - dayOfWeek;
    }

    /**
     * 获得距当前时间所在某星期的周一的日期 例： 0-本周周一日期 -1-上周周一日期 1-下周周一日期
     * @param week
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static Date getMondayOfWeek(int week) {
        int mondayPlus = getMondayPlus(); // 相距周一的天数差
        GregorianCalendar current = new GregorianCalendar();
        current.add(Calendar.DATE, mondayPlus + (7 * week));
        return current.getTime();
    }

    /**
     * 获得某日前后的某一天
     * @param date
     * @param day
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static Date getDay(Date date, int day) {
        GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        c.add(Calendar.DATE, day);
        return c.getTime();
    }

    /**
     * 获得距当前周的前后某一周的日期
     * @param week
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String[] getDaysOfWeek(int week) {
        String[] days = new String[7];
        Date monday = getMondayOfWeek(week); // 获得距本周前或后的某周周一
        Timestamp t = new Timestamp(monday.getTime());
        days[0] = getStrDate(t);
        for (int i = 1; i < 7; i++) {
            t = new Timestamp(getDay(monday, i).getTime());
            days[i] = getStrDate(t);
        }
        return days;
    }

    /**
     * MCC的UTC时间转换，MCC的UTC不是到毫秒的
     * @param utc
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static Date mccUTC2Date(long utc) {
        Date d = new Date();
        d.setTime(utc * 1000); // 转成毫秒
        return d;
    }

    /**
     * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
     * @param strDate
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static Date strToDateLong(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        if (strtodate == null) {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            strtodate = formatter.parse(strDate, pos);
        }
        return strtodate;
    }

    /**
     * 将 yyyy-MM-dd HH:mm 格式字符串转换为时间
     * @param strDate
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static Date strToDateTime(String strDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        ParsePosition pos = new ParsePosition(0);
        Date strtodate = formatter.parse(strDate, pos);
        if (strtodate == null) {
            formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            strtodate = formatter.parse(strDate, pos);
        }
        return strtodate;
    }

    /**
     * 根据输入的字符串返回日期字符串 2006-07-07 22:10 2006-07-07
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String getStrDate(String str) {
        if (str.length() > 10) {
            String[] string = str.trim().split(" ");
            return string[0];
        }
        else {
            return getCurrentDate();
        }
    }

    /**
     * 获取当前时间的字符串 2006-07-07 22:10:10 2006-07-07_221010
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String getStrDateTime() {
        Timestamp d = crunttime();
        return d.toString().substring(0, 19).replace(":", "").replace(" ", "_");
    }

    /**
     * 根据日期字符串，返回今天，昨天或日期
     * @param str
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String getDayOrDate(String str) {
        if ((str != null) && !str.equals("")) {
            if (getNextDay(str, 0).equals(getCurrentDate())) {
                str = "今天";
            }
            else if (getNextDay(str, 1).equals(getCurrentDate())) {
                str = "昨天";
            }
        }
        return str;
    }

    /**
     * 返回当前日期所在星期，2对应星期一
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static int getMonOfWeek() {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        return cal1.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取当前日期之前的日期字符串 如 2007-04-15 前5月 就是 2006-11-15
     * @param month
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static String getPreviousMonth(int month) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal1.add(Calendar.MONTH, -month);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(cal1.getTime());
    }

    public static String getStrYear(int year) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal1.add(Calendar.YEAR, -year);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        return formatter.format(cal1.getTime()) + "年份";
    }

    /**
     * 比较两个日期前后 可以大于或等于
     * @param starDate
     * @param endDate
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static boolean compareTwoDays(String starDate, String endDate) {
        Calendar cal_start = Calendar.getInstance();
        Calendar cal_end = Calendar.getInstance();
        cal_start.setTime(parseDate(starDate));
        cal_end.setTime(parseDate(endDate));
        return cal_end.after(cal_start);
    }

    public static int getDaysBetween(java.util.Calendar d1, java.util.Calendar d2) {
        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
            java.util.Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int days = d2.get(java.util.Calendar.DAY_OF_YEAR) - d1.get(java.util.Calendar.DAY_OF_YEAR);
        int y2 = d2.get(java.util.Calendar.YEAR);
        if (d1.get(java.util.Calendar.YEAR) != y2) {
            d1 = (java.util.Calendar) d1.clone();
            do {
                days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
                d1.add(java.util.Calendar.YEAR, 1);
            }
            while (d1.get(java.util.Calendar.YEAR) != y2);
        }
        return days;
    }

    /**
     * 得到两个日期之间的年
     * @param starDate
     * @param endDate
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static int dateDiffYear(String starDate, String endDate) {
        int result = 0;
        Calendar d1 = Calendar.getInstance();
        Calendar d2 = Calendar.getInstance();
        d1.setTime(parseDate(starDate));
        d2.setTime(parseDate(endDate));
        // 日期大小翻转
        if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
            java.util.Calendar swap = d1;
            d1 = d2;
            d2 = swap;
        }
        int yy = d2.get(Calendar.YEAR) - d1.get(Calendar.YEAR);
        int mm = d2.get(Calendar.MONTH) - d1.get(Calendar.MONTH);
        if (mm < 0) {
            result = yy - 1;
        }
        if (mm > 0) {
            result = yy;
        }
        if (mm == 0) {
            if ((d2.getTimeInMillis() - d1.getTimeInMillis()) >= 0) {
                result = yy;
            }
            else {
                result = yy - 1;
            }
        }
        return result;
    }

    /**
     * 获取年龄 〈一句话功能简述〉 〈功能详细描述〉
     * @param starDate
     * @return
     * @see [类、类#方法、类#成员]
     * @since [产品/模块版本]
     */
    public static int getAgeByBirth(String starDate) {
        return dateDiffYear(starDate, getCurrentDate());
    }
}
