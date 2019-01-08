package com.trotyzyq.common.util;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串处理工具类
 * @author xutao
 * @date 2017-04-06 10:22
 */
public class StringUtil {

    /** 手机号正则表达式 */
    private static final Pattern mobilePattern = Pattern.compile("^((166)|(19[8,9])|(13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(14[5-9]))[0-9]{8}$");



    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }


    /**
     * 判断字符串是否为空
     */
    public static boolean isBlank(String str) {
        int strLen;
        if(str != null && (strLen = str.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }


    /**
     * 判断多个字符串是否为空
     */
    public static boolean nonBlankCheck(String... ss) {
        for (String s : ss) {
            if (isBlank(s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasNotBlank(String... ss) {
        for (String s : ss) {
            if (!isBlank(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否为手机号
     */
    public static boolean isValidMobileNumber(String str) {
        Matcher m = mobilePattern.matcher(str);
        return m.matches();
    }

    /**
     * 获取长度为len的随机数字字符串
     */
    public static String randNumberStr(int len){
        Double d = Math.pow(10, len);
        return StringUtils.leftPad(RandomUtils.nextInt(d.intValue() - 1) + "", len, "0");
    }

    public static String randNumberLetterStr(int length) {
        if (length <= 0) {
            return "";
        }

        char str1[] = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z' };
        char str2[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (RandomUtils.nextInt(2) == 0) {
                sb.append(str1[RandomUtils.nextInt(str1.length)]);
            } else {
                sb.append(str2[RandomUtils.nextInt(str2.length)]);
            }
        }
        return sb.toString();
    }
    public static int getRandomNumExcept0(int number){
       return RandomUtils.nextInt(number)+1;

    }

    /**
     * 将字符串替换
     * @param beRepalcedStr 需要被替换的字符串
     * @param map 被替换的表达式 和 字符
     * @return 替换后的字符串
     */
    public String replaceStrByMap(String beRepalcedStr, Map<String,String> map){
        Iterator iterator=map.keySet().iterator();
        while (iterator.hasNext()){
            String key=(String)iterator.next();
            String value=map.get(key);
            beRepalcedStr=beRepalcedStr.replace(key,value);
        }
        return beRepalcedStr;
    }

    /**
     * 对字符串进行处理，把'null'、'NULL'处理成''空字符串，非空的字符串会执行trim
     * @param paramStr  待处理的字符串
     * @return  处理后的字符串
     */
    public static String stringNullHandle(String paramStr){
        if(ValidateUtil.isNullOrEmpty(paramStr)){
            return paramStr.trim();
        }else{
            return "";
        }
    }

}
