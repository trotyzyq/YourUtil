package com.trotyzyq.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @Author zyq on 2018/11/6.
 */
public class DateUtil2 {
    /** TimeStamp只支持这种格式**/
    private String format = "yyyy-MM-dd HH:mm:ss";

    /**
     * timeStamp转时间字符串，默认格式
     * @param time
     * @return
     */
    private String timeStampToString(Timestamp time){
       return timeStampToString(time,format);
    }

    /**
     * timeStamp转时间字符串，自定义格式
     * @param time
     * @return
     */
    private String timeStampToString(Timestamp time ,String format){
        DateFormat sdf = new SimpleDateFormat(format);
        String tsStr = sdf.format(time);
        return tsStr;
    }

    /**
     * 字符串转TimeStamp
     * @param time
     * @return
     */
    public static Timestamp stringToTimeStamp(String time){
        Timestamp ts = Timestamp.valueOf(time);
        return  ts;
    }

    public static void main(String[] args) {
        System.err.println( stringToTimeStamp("2018-12-12 10:23:24.0"));
    }
}
