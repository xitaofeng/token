package com.lang.token.util.date;


import com.lang.token.util.StringUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author liu_yeye
 * @date 2018-05-14 14:46
 */
public class DateTimeUtils {
    public  static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm";
 //   public static  final String MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    public static  final String SINGLE_MINUTE_PATTERN = "yyyy-M-dd HH:mm";
    public static   final String HOUR_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static  final String DATE_PATTERN = "yyyy-MM-dd";
    public static  final String CH_DATE_PATTERN = "yyyy年MM月dd日";
    public static  final String CH_DATE_MONTH_PATTERN = "MM月dd日";
    public static   final String MONTH_PATTERN = "yyyy-MM";
    public static   final String YEAR_PATTERN = "yyyy";
    public static   final String HOUR_MINUTE_ONLY_PATTERN = "HH:mm";
    public  static   final String HOUR_ONLY_PATTERN = "HH";
    private DateTimeUtils(){

    }
    public static DateToString getStringInstance(){
        return DateToString.DATETOSTRING;
    }
    public static StringToDate getDateInstance(){
        return StringToDate.STRINGTODATE;
    }

    /**
     * date转化string
     */
    public static class DateToString{
      private  SimpleDateFormat sdf = null;

        public  String  dateFormat(Date date, String pattern) {
            if(StringUtils.isEmpty(pattern)){
                pattern = DateTimeUtils.DATE_TIME_PATTERN;
            }
            sdf = new SimpleDateFormat(pattern);
            return sdf.format(date);
        }
        public  String  dateDefaultFormat(Date date) {
            return dateFormat(date,DateTimeUtils.DATE_TIME_PATTERN);
        }
      static   final DateToString DATETOSTRING = new DateToString();
    }
    /**
     * string转化date
     */
    public static class StringToDate{
        public  Date dateParse(String dateTimeString, String pattern) throws ParseException {
            if(StringUtils.isEmpty(pattern)){
                pattern = DateTimeUtils.DATE_TIME_PATTERN;
            }
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(dateTimeString);
        }

        public Date dateDefaultParse(String dateTimeString) throws ParseException {
            return dateParse(dateTimeString,DateTimeUtils.DATE_TIME_PATTERN);
        }
        public Timestamp timestampFormat(Date date) throws ParseException{
            String s = DateToString.DATETOSTRING.dateDefaultFormat(date);
            return new Timestamp(dateDefaultParse(s).getTime());
        }
        public Date timestampParse(Timestamp timestamp) throws ParseException{
            SimpleDateFormat sdf = new SimpleDateFormat(DateTimeUtils.DATE_TIME_PATTERN);
            String tsStr = sdf.format(timestamp);
            return dateDefaultParse(tsStr);
        }
        /**
         *  @param date
         * @param day 想要获取的日期与传入日期的差值 比如想要获取传入日期前四天的日期 day=-4即可
         * @return
         */
     public  Date getSomeDay(Date date, int day){
         Calendar calendar = Calendar.getInstance();
         calendar.setTime(date);
         calendar.add(Calendar.DATE, day);
         return calendar.getTime();
     }
        static   final StringToDate STRINGTODATE = new StringToDate();
    }
}
