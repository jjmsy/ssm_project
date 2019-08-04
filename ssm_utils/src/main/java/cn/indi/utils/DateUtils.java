package cn.indi.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换工具类
 */
public class DateUtils {

    /**
     * 日期转换成字符串
     * @param date 要转换的日期
     * @param pattern 日期的转换格式
     * @return
     */
    public static String date2String(Date date,String pattern){
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        String format = dateFormat.format(date);
        return format;
    }
    //字符串转换成日期
    public static Date String2Date(String str,String pattern) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date parse = dateFormat.parse(str);
        return parse;

    }
}
