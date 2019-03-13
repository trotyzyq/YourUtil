/*
 * 文 件 名: DateUtil.java 版 权: Dream Co., Ltd. Copyright YYYY-YYYY, All rights reserved 描 述: <描述> 修 改
 * 人: Owen 修改时间: -- 跟踪单号: <跟踪单号> 修改单号: <修改单号> 修改内容: <修改内容>
 */
package com.trotyzyq.common.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * 金钱工具类
 *
 * @author johny
 */
public class MoneyUtil {

    private static Logger logger = LoggerFactory.getLogger(MoneyUtil.class);

    /**
     * 元转化为分
     *
     * @param
     * @return
     */
    public static Long convertYuanToFen(String yuan) {
        if (StringUtils.isBlank(yuan) || "null".equals(yuan)) {
            return 0l;
        }
        BigDecimal dec = new BigDecimal(yuan);
        BigDecimal result = dec.multiply(new BigDecimal(100));
        return result.longValue();
    }

    /**
     * 元转化为分
     *
     * @param
     * @return
     */
    public static String convertYuanToFenStr(String yuan) {
        if (StringUtils.isBlank(yuan)|| "null".equals(yuan)) {
            return "0";
        }
        BigDecimal dec = new BigDecimal(yuan);
        BigDecimal result = dec.multiply(new BigDecimal(100));
        return result.setScale(0,BigDecimal.ROUND_DOWN).toString();
    }

    public static void main(String[] arg) {

//        String a = "-460";
//        String b = convertStr2Long(a);
//        System.out.print(b);

    }

    /**
     * 元转化为分,并且四舍五入
     *
     * @param
     * @return
     */
    public static Long convertYuanToFenHalf(String yuan) {
        if (StringUtils.isBlank(yuan)|| "null".equals(yuan)) {
            return 0l;
        }
        BigDecimal dec = new BigDecimal(yuan);
        BigDecimal fen = dec.setScale(2, BigDecimal.ROUND_HALF_UP);
        BigDecimal result = fen.multiply(new BigDecimal(100));
        return result.longValue();
    }

    /**
     * 元转化为毫
     *
     * @param
     * @return
     */
    public static Long convertYuanToHao(String yuan) {
        if (StringUtils.isBlank(yuan)|| "null".equals(yuan)) {
            return 0l;
        }
        BigDecimal dec = new BigDecimal(yuan);
        BigDecimal result = dec.multiply(new BigDecimal(10000));
        return result.longValue();
    }

    /**
     * 字符转换为数字
     *
     * @param
     * @return
     */
    public static long convertStr2Long(String val) {
        if (StringUtils.isBlank(val)|| "null".equals(val)) {
            return 0l;
        }
        try {
            BigDecimal dec = new BigDecimal(val);
            return dec.longValue();
        } catch (Exception e) {
            logger.error("money val" + val, e);
        }
        return 0l;
    }

    /**
     * 分转化为元
     *
     * @param
     * @return
     */
    public static String convertFenToYuan(String fen) {
        if (StringUtils.isBlank(fen)|| "null".equals(fen)) {
            return "0";
        }
        final int MULTIPLIER = 100;
        return new BigDecimal(fen).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();
    }


    /**
     * 分转化为元
     *
     * @param
     * @return
     */
    public static String convertFenToYuan(Long fen) {
        if (fen == null) {
            return "";
        }
        final int MULTIPLIER = 100;
        return new BigDecimal(fen).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();
    }

    /**
     * 毫转化为元
     *
     * @param
     * @return
     */
    public static String convertHaoToYuan(String fen) {
        if (StringUtils.isBlank(fen)|| "null".equals(fen)) {
            return "";
        }
        final int MULTIPLIER = 10000;
        return new BigDecimal(fen).divide(new BigDecimal(MULTIPLIER)).toString();
    }
}
