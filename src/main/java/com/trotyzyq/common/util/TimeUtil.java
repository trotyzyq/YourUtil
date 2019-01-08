package com.trotyzyq.common.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.trotyzyq.common.bo.Holiday;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by trotyzyq on 2018/8/13.
 * 时间计算工具类
 */
public class TimeUtil {
    static Logger logger= Logger.getLogger(TimeUtil.class);

    /** 时间格式**/
    private static String format = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取当前时间戳，秒为单位
     * @return  1234567 s
     */
    public static long getCurrentTime(){
        return System.currentTimeMillis()/1000;
    }
    /**
     * 获取当前时间戳，毫秒为单位
     * @return  1234567 ms
     */
    public static long getCurrentTimeMills(){
        return System.currentTimeMillis();
    }

    /**
     * 时间戳秒数 转timeStamp
     * @param timeStamp
     * @return
     */
    public static Timestamp stampToTimeStamp(long timeStamp){
        String s = stampToDate(timeStamp);
        return  stringToTimeStamp(s);
    }

    /**
     * 获取当前时间字符串
     * @return 2018-12-12 01:01:01
     */
    public static String getCurrentTimeString(){
        return stampToDate(System.currentTimeMillis()/1000);
    }

    /**
     * 时间戳毫秒数转时间字符串
     * @param time  12345678s
     * @return 2018-12-12 01:01:01
     */
    public static String stampMilssToDate(long time){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 时间戳秒数转时间字符串
     * @param time  12345678s
     * @return 2018-12-12 01:01:01
     */
    public static String stampToDate(long time){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time * 1000);
        res = simpleDateFormat.format(date);
        return res;
    }


    /**
     * 将时间字符串 转换为时间戳(格式为字符串)
     * @param dateTime 2018-12-12 01:01:01
     * @return  12345678 (String)
     */
    public static String dateToStamp(String dateTime)  {
        return String.valueOf(dateToStampWithLong(dateTime)/1000);
    }

    /**
     * 将时间(字符串) 转换为时间戳 秒
     * @param dateTime 2018-12-12 01:01:01
     * @return 12345678 (Long)
     */
    public static long dateToStampWithLong(String dateTime)  {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime()/1000;
        return ts;
    }

    /**
     * 将时间(字符串) 转换为时间戳 毫秒
     * @param dateTime 2018-12-12 01:01:01
     * @return 12345678000 (Long)
     */
    public static long dateToStampWithLongMills(String dateTime)  {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = simpleDateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long ts = date.getTime();
        return ts;
    }


    /**
     * timeStamp转时间字符串
     * @param time
     * @param format
     * @return 2018-12-12 01:01:01
     */
    private static String timeStampToTimeString(Timestamp time , String format){
        DateFormat sdf = new SimpleDateFormat(format);
        String tsStr = sdf.format(time);
        return tsStr;
    }

    /**
     * 时间字符串转TimeStamp 固定格式yyyy-MM-dd HH:mm:ss或者yyyy-MM-dd HH:mm:ss.SSS
     * @param time 2018-12-12 01:01:01
     * @return
     */
    public static Timestamp stringToTimeStamp(String time){
        Timestamp ts = Timestamp.valueOf(time);
        return  ts;
    }

    /**
     * timeStamp转时间戳
     * @param timestamp
     * @return 12345678 s
     */
    public static Long timeStampToLong(Timestamp timestamp){
        String str =timeStampToTimeString(timestamp, "yyyy-MM-dd HH:mm:ss");
        return dateToStampWithLong(str);
    }

    /**获取当前时间的小时数**/
    public static Integer getCurrentTimeHour( )  {
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=df.format(day);
        int hour=Integer.parseInt(time.substring(11,13));
        return hour;
    }

    /**获取当前小时时间的下一个小时**/
    public static Integer getCurrentTimeNextHour( )  {
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=df.format(day);
        int hour=Integer.parseInt(time.substring(11,13));
        return hour+1;
    }

    /**获取当前小时时间的下一个小时的时间戳**/
    public static Long getCurrentTimeNextHourStamp( )  {
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=df.format(day);
        int hour=Integer.parseInt(time.substring(11,13));
        String before = time.substring(0,11);
        String after = ":00:00";
        return dateToStampWithLong(before+(hour+1+"")+after);
    }

    /**获取当前小时时间的指定小时的时间戳**/
    public static Long getCurrentTimeDecideHourStamp(int h){
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time=df.format(day);
        int hour=Integer.parseInt(time.substring(11,13));
        String before = time.substring(0,11);
        String after = ":00:00";
        return dateToStampWithLong(before+(h+"")+after);
    }

    /**
     * 指定时间的指定小时的时间戳 秒
     * @param time 时间
     * @param h 需要的小时数
     * @return 123456789 s
     */
    public static Long getTimeDecideHourStamp(String time,int h){
        int hour=Integer.parseInt(time.substring(11,13));
        String before = time.substring(0,11);
        String after = ":00:00";
        return dateToStampWithLong(before+(h+"")+after);
    }

    /**
     * 指定时间的指定小时的时间戳 毫秒
     * @param time 时间
     * @param h 需要的小时数
     * @return 123456789 ms
     */
    public static Long getTimeDecideHourStampMills(String time,int h){
        int hour=Integer.parseInt(time.substring(11,13));
        String before = time.substring(0,11);
        String after = ":00:00";
        return dateToStampWithLongMills(before+(h+"")+after);
    }

    /**
     * 获取指定的时间是星期几
     * @param time 时间字符串
     * @return 1 周末 2周一
     */
    public static int getWeekByTime(String time){
        Calendar calendar = Calendar.getInstance();
        long mills = dateToStampWithLongMills(time);
        calendar.setTimeInMillis(mills);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取指定的时间戳是星期几
     * @param mills  毫秒数
     * @return
     */
    public static int getWeekByStamp(long mills){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取指定的时间戳是否是周六周日
     * @param mills  毫秒数
     * @return 是 true 否 false
     */
    public static boolean checkWeekByMills(long mills){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mills);
        int time = calendar.get(Calendar.DAY_OF_WEEK);
        if(time == 1 || time == 7){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 获取指定的时间戳是否是周六周日
     * @param time  时间字符串 2018-01-01 01:01:01
     * @return 是 true 否 false
     */
    public static boolean checkWeek(String time){
        Calendar calendar = Calendar.getInstance();
        long mills = dateToStampWithLongMills(time);
        calendar.setTimeInMillis(mills);
        int time1 = calendar.get(Calendar.DAY_OF_WEEK);
        if(time1 == 1 || time1 == 7){
            return true;
        }else {
            return false;
        }
    }



    /**
     * 计算指定的时间内有多少个休息日
     *
     */
    public static int hasHowManyWeek(Timestamp startTime, Timestamp endTime){
        int count = 0;
        String start = timeStampToTimeString(startTime, format);
        String end = timeStampToTimeString(endTime, format);

        /** 转换为毫秒时间戳**/
        long startMills = dateToStampWithLongMills(start);
        long endMills = dateToStampWithLongMills(end);

        long endMixMills = getTimeDecideHourStampMills(end,24);
        while(startMills < endMixMills){
            int isWeek = getWeekByStamp(startMills);
            /** 如果是双休日 数量加一 ，同时修改开始时间戳**/
            if(isWeek == 7 || isWeek == 1){
                count ++;
            }
            startMills =  startMills + 24 * 3600 * 1000;
        }
        return count;
    }

    /**
     * 得到节假日日期列表
     * @param ye
     * @param mon
     * @return
     */
    public static List<Holiday> getHoliday(int ye, int mon){
        String year = ye + "";
        String month = mon + "";
        String url = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query=" + year + "年" + month +"月&resource_id=6018&format=json";
        String result = HttpsClient.getSSL(url,"utf-8");
        JSONArray dataArray = JSON.parseObject(result).getJSONArray("data");


        Object o = dataArray.getJSONObject(0).get("holiday");
        if(o == null){
            return null;
        }
        /** holiday可能是JSONOBJECT**/
        JSONArray holidayArrAy = new JSONArray();
        if(o instanceof  JSONObject){
            holidayArrAy.add(o);
        }
        if(o instanceof JSONArray){
            holidayArrAy = (JSONArray)o;
        }

        List<Holiday> holidayList = new ArrayList<>();
        Iterator iterator = holidayArrAy.iterator();
        while(iterator.hasNext()){
            JSONObject holidayObject = (JSONObject)iterator.next();
            String fes = holidayObject.getString("festival");
            String subMonth = fes.split("-")[1];
            /** 如果不是当前月份的，排除掉**/
            if(!subMonth.equals(month)){
                continue;
            }
            JSONArray listArray = holidayObject.getJSONArray("list");
            List<Holiday> holidayList1 = listArray.toJavaList(Holiday.class);
            Iterator iterator1 = holidayList1.iterator();
            while(iterator1.hasNext()){
                Holiday holiday = (Holiday) iterator1.next();
                /** 取出data list中不是这个月份和年份的**/
                String date = holiday.getDate();
                String[] dateSplit = date.split("-");

//                if (!dateSplit[0].equals(year)) {
//                    iterator1.remove();
//                    continue;
//                }
//
//                if (!dateSplit[1].equals(month)) {
//                    iterator1.remove();
//                }
                /** 单位数 + 0**/
                String month1 = dateSplit[1];
                if (month1.length() < 2) {
                    month1 = "0" + month1;
                }

                String day = dateSplit[2];
                if (day.length() < 2) {
                    day = "0" + day;
                }
                holiday.setDate(dateSplit[0] + "-" + month1 + "-" + day);
            }
            holidayList.addAll(holidayList1);
        }
        return holidayList;
    }

    /**
     * 得到节假日日期列表
     * @param startTime 开始时间 2018-12-12 01:01:01
     * @param endTime 结束时间 2018-12-12 01:01:01
     * @return
     */
    public static List<Holiday> getHolidayByStartAndEnd(String startTime, String endTime){
        List<Holiday>  holidayList = new ArrayList<>();
        int startYear = Integer.parseInt(startTime.substring(0, 4)) ;
        int startMonth = Integer.parseInt(startTime.substring(5,7))  ;

        int endYear = Integer.parseInt(endTime.substring(0, 4)) ;
        int endMonth = Integer.parseInt(endTime.substring(5,7)) ;
        /** 如果是同一年**/
        if(startYear == endYear){
            for(int i = startMonth ; i <= endMonth;i++){
                holidayList.addAll(getHoliday(startYear, i));
            }
            return holidayList;
        }
        if(startYear < endYear){
            /** 从2017 年到2018年 底**/
            for(int i = startYear; i < endYear; i++){
                for(int j = startMonth ; j <= 12;j++){
                    List l = getHoliday(i, j);
                    if(l != null && l.size() > 0 ){
                        holidayList.addAll(l);
                    }
                }
            }
            /**2018年到最后 **/
            for(int j = 1 ; j <= endMonth;j++){
                List l = getHoliday(endYear, j);
                if(l != null && l.size() > 0 ){
                    holidayList.addAll(l);
                }
            }
        }
        return holidayList;
    }

    /**
     * 计算这一天是否在节假日列表中
     */
    public static boolean checkInHolidayList(String time, List<Holiday> holidayList){
        for(Holiday holiday : holidayList){
            String timeDay = time.substring(0, 10);
            if(timeDay.equals(holiday.getDate())){
                return true;
            }
        }
        return false;
    }

    /**
     * 获取时间间隔
     */
    public static List<Long> getTimeCut(Timestamp startTime,Timestamp endTime,String startWork, String endWork, List<Holiday> holidayList){

        long firstDayTime = 0;
        long endDayTime = 0;
        int weekAndHolidayCount = 0;
        /** 项目开始时间时间戳**/
        String start = timeStampToTimeString(startTime, "yyyy-MM-dd HH:mm:ss");
        long startStamp = dateToStampWithLong(start);

        /** 项目结束时间时间戳**/
        String end = timeStampToTimeString(endTime, "yyyy-MM-dd HH:mm:ss");
        long endStamp = dateToStampWithLong(end);


        /** 工作周期开始的时间戳**/
        String startBefore = start.substring(0,11);
        String endBefore = end.substring(0,11);

        long startWorkStamp = dateToStampWithLong(startBefore + startWork );

        /** 工作周期结束的时间戳**/
        long endWorkStamp = dateToStampWithLong(startBefore + endWork);

        /** 工作周期的秒数**/
        long workDaySeconds = endWorkStamp - startWorkStamp;

        /** 计算中间的天数 **/
        String startDay = start.substring(8, 10);
        String endDay = end.substring(8, 10);


        /** 用于计算是否是同一天的字段**/
        long calStartStamp = startStamp;
        long calEndStamp = endStamp;
        String calStartDate = stampToDate(calStartStamp).substring(0,10);
        String catEndDate = stampToDate(calEndStamp).substring(0,10);
        Set timeSet = new HashSet();

        /** 必须不再同一天**/
        while((calEndStamp - calStartStamp > 3600 * 24) || !calStartDate.equals(catEndDate)){
            /** 下一天 时间戳和字符串date **/
            calStartStamp = calStartStamp + 3600 * 24;
            calStartDate = stampToDate(calStartStamp).substring(0,10);;

            /** 下一天时间字符串的天数**/
            String time = stampToDate(calStartStamp);
            String timeDay = time.substring(0, 10);
            boolean isWeek = checkWeek(time);

            if(isWeek){
                weekAndHolidayCount ++ ;
                timeSet.add(timeDay);
            }
            for(Holiday holiday : holidayList){
                /** 计算是否是双休日 或是节假日**/
                String holidayDate = holiday.getDate() + " ";
                if(holidayDate.equals(startBefore) || holidayDate.equals(endBefore)){
                    continue;
                }
                if(timeDay.equals(holiday.getDate())){
                    if(timeSet.contains(timeDay)){
                        break;
                    }
                    weekAndHolidayCount ++ ;
                    break;
                }
            }
        }

        /** 如果是在一天之内的**/
        if((endStamp - startStamp) <= 3600 * 24 && startDay.equals(endDay)){
            long all = 0;
            /** 如果这一天就是非工作日**/
            if(checkWeek(start) || checkInHolidayList(start, holidayList)){
                all = 0;
            }else{
                /** 如果这一天不是非go你工作日**/
                if(startStamp <= startWorkStamp){
                    if(endStamp <= startWorkStamp){
                        all = 0;
                    }
                    if(endStamp >= startWorkStamp){
                        all = endStamp - startWorkStamp;
                    }
                    if(endStamp >= endWorkStamp){
                        all = endWorkStamp - startWorkStamp;
                    }
                }
                if(startStamp >= startWorkStamp && startStamp <= endWorkStamp){
                    if(endStamp <= endWorkStamp){
                        all = endStamp - startStamp;
                    }
                    if(endStamp >= endWorkStamp){
                        all = endWorkStamp - startStamp;
                    }
                }
                if(startStamp >= endWorkStamp){
                    all = 0;
                }
            }
            long day = all / workDaySeconds;
            long hour = ( all % workDaySeconds) /3600;
            long minute = (( all % workDaySeconds) % 3600 / 60);
            List<Long> integerList = new ArrayList<>();
            integerList.add(day);
            integerList.add(hour);
            integerList.add(minute);
            return integerList;
        }


        if(startStamp <= startWorkStamp){
            firstDayTime = endWorkStamp - startWorkStamp;
        }
        if(startStamp > startWorkStamp && startStamp <= endWorkStamp){
            firstDayTime = endWorkStamp - startStamp;
        }
        if(startStamp >= endWorkStamp){
            firstDayTime = 0;
        }

        /** 如果开始结束时间就是非工作日 结算firstDay和endDay**/
        if(checkWeek(start)){
            firstDayTime = 0;
        }
        for(Holiday holiday : holidayList){
            String timeDay = start.substring(0, 10);
            if(timeDay.equals(holiday.getDate())){
                firstDayTime = 0 ;
                break;
            }
        }
        if(checkWeek(end)){
            endDayTime = 0;
        }
        for(Holiday holiday : holidayList){
            String timeDay = end.substring(0, 10);
            if(timeDay.equals(holiday.getDate())){
                endDayTime = 0 ;
                break;
            }
        }


        long newStartDayStamp = getTimeDecideHourStamp(start, 24);
        long newEndDayStamp = getTimeDecideHourStamp(end,00);
        /** 新的工作周期开始的时间戳**/
        endBefore = end.substring(0,11);
        startWorkStamp = dateToStampWithLong(endBefore + startWork );

        /** 新的工作周期结束的时间戳**/
        endWorkStamp = dateToStampWithLong(endBefore + endWork);

        if(endStamp <= startWorkStamp){
            endDayTime = 0;
        }
        if(endStamp >= startWorkStamp && endStamp <=endWorkStamp){
            endDayTime = endStamp - startWorkStamp;
        }
        if(endStamp > endWorkStamp){
            endDayTime = endWorkStamp - startWorkStamp;
        }
        long newSeconds = (long)((newEndDayStamp - newStartDayStamp - weekAndHolidayCount * 3600 * 24) * (workDaySeconds / ( 3600 * 24 * 1.0)));
        long allTime = firstDayTime + endDayTime + newSeconds;

        long day = allTime / workDaySeconds;
        long hour = ( allTime % workDaySeconds) /3600;
        long minute = (( allTime % workDaySeconds) % 3600 / 60);
        List<Long> integerList = new ArrayList<>();
        integerList.add(day);
        integerList.add(hour);
        integerList.add(minute);
        return integerList;
    }
    public static void main(String[] args) {
//        String start = "2018-12-13 18:12:57.0";
//        String end = "2018-12-13 22:34:30.0";
//        Timestamp startTimeStamp = stringToTimeStamp(start);
//        Timestamp endTimeStamp = stringToTimeStamp(end);
//        String startWork = "09:00:00";
//        String endWork = "18:00:00";
//        System.out.println(getTimeCut(startTimeStamp, endTimeStamp, startWork, endWork));
//        System.out.println(getTimeCut(startTimeStamp, endTimeStamp, startWork, endWork));


        String start = "2017-01-01 00:00:00";
        String end = "2018-12-31 24:00:00";
        List<Holiday> holidayList = TimeUtil.getHolidayByStartAndEnd(start, end);

        /** 过滤**/
        Set<Holiday> set = new HashSet();
        for(Holiday holiday: holidayList){
            set.add(holiday);
        }
        /** 排序**/
        List<Holiday> newList  = new ArrayList<>();
        set.forEach((holiday) ->{
            String date = holiday.getDate();
            Timestamp dateTime = stringToTimeStamp(date + " 12:00:00");
            holiday.setDateTime(dateTime);
            newList.add(holiday);
        });
        newList.sort((Holiday h1, Holiday h2)-> h1.getDateTime().compareTo(h2.getDateTime()));
        System.out.println(1);
    }
}
