package com.geekplus.rms.analysis.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    //格式化输出日期
    static DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static DateFormat df = new SimpleDateFormat("yyyyMMdd");

    static DateFormat df_ = new SimpleDateFormat("yyyy-MM-dd");


    public static String getDateStr(Integer n){
        if(n==null){
            n=0;
        }
        Date now = new Date();
        long time = n*24*60*60*1000;//60秒
        Date date = new Date(now .getTime() + time);//60秒后的时间
        return  df.format(date);
    }

    public static Date getDate(Integer n){
        if(n==null){
            n=0;
        }
        Date now = new Date();
        long time = n*24*60*60*1000;//60秒
        Date date = new Date(now.getTime() + time);//60秒后的时间
        return  date;
    }

    public static String getDateStr(Long time){
        if(time==null){
            time=0L;
        }
        Date date = new Date(time);
        return  df.format(date);
    }

    public static String getCurrentDateStr(Integer n){
        if(n==null){
            n=0;
        }
        Date now = new Date();
        long time = n*24*60*60*1000;//60秒
        Date date = new Date(now.getTime() + time);
        return  df_.format(date);
    }

    public static void main(String[] args) {
        System.out.println(getDateStr(-2));
        System.out.println(getDateStr(-1));
        System.out.println(getDateStr(0));
        System.out.println(getDateStr(1));

        System.out.println(getDateStr(2));

        System.out.println(df_.format(getDate(2)));

        String currentDateStr = DateUtil.getCurrentDateStr(-1 + 1);

        System.out.println("currentDateStr: "+currentDateStr);
    }

}
