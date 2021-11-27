package com.geekplus.rms.analysis.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 考虑到SimpleDateFormat为线程不安全对象，故应用ThreadLocal来解决，
 * 使SimpleDateFormat从独享变量变成单个线程变量
 */
public class ThreadLocalDateUtil {

    private static final String date_format = "yyyy-MM-dd HH:mm:ss";

    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();

    public static DateFormat getDateFormat() {
        DateFormat df = threadLocal.get();
        if(df == null){
            df = new SimpleDateFormat(date_format);
            threadLocal.set(df);
        }
        return df;
    }

    public static String formatDate(Date date) throws ParseException {
        return getDateFormat().format(date);
    }

    public static Date parse(String strDate) throws ParseException {
        return getDateFormat().parse(strDate);
    }



}
