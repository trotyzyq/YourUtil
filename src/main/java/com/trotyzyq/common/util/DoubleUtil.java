package com.trotyzyq.common.util;


import com.google.common.base.MoreObjects;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by trotyzyq
 * Double计算的工具类
 */

public class DoubleUtil {
    /**
     * 将double分成随机的n份
     * @param sum double
     * @param size 随机分成size份
     * @return
     */
    public static List<Double> getRandomList(Double sum,Integer size){
        RedPackage redPackage=new RedPackage();
        redPackage.setRemainSize(size);
        redPackage.setRemainMoney(sum);
        List<Double> arrayList=new ArrayList<Double>();
        for(int i=0;i<size;i++){
            arrayList.add(getRandomMoney(redPackage));
        }
        return arrayList;
    }

    private static double getRandomMoney(RedPackage _redPackage){
        // remainSize 剩余的红包数量
        // remainMoney 剩余的钱
        if (_redPackage.remainSize == 1) {
            _redPackage.remainSize--;
            return (double) Math.round(_redPackage.remainMoney * 100) / 100;
        }
        Random r     = new Random();
        double min   = 1; //
        double max   = _redPackage.remainMoney / _redPackage.remainSize * 2;
        double money = r.nextDouble() * max;
        money = money <= min ? 1: money;
        money = Math.floor(money * 1) / 1;
        _redPackage.remainSize--;
        _redPackage.remainMoney -= money;
        return money;
    }


    /**
     * double加法
     * @return "10"
     */
    public static  double doubleAdd(String a,String b){
        if(StringUtils.isBlank(a) || StringUtils.isBlank(b)){
            throw new RuntimeException("入参不可为null或者空");
        }
        BigDecimal b1 = new BigDecimal(a);
        BigDecimal b2 = new BigDecimal(b);
        return b1.add(b2).doubleValue();
    }


    /**
     * double加法
     * @return 0.0
     */
    public static  double doubleAdd(Double a,Double b){
        if(a == null || b == null){
            throw new RuntimeException("入参不可为null");
        }
        return doubleAdd(String.valueOf(a),String.valueOf(b));
    }

    /**Double的加**/
    public static  double doubleAdd(Double a,Double b,Double c){
        return doubleAdd(doubleAdd(a,b),c);
    }



    /**
     * double 相减
     */
    public static double doubleSub(String a,String b){
        if(StringUtils.isBlank(a) || StringUtils.isBlank(b)){
            throw new RuntimeException("入参不可为null或者空");
        }

        BigDecimal bd1 = new BigDecimal(a);
        BigDecimal bd2 = new BigDecimal(b);
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double 相减
     */
    public static double doubleSub(Double a,Double b){
        if(a == null || b == null){
            throw new RuntimeException("入参不可为null");
        }
        return doubleSub(String.valueOf(a),String.valueOf(b));
    }

    /**
     * double乘法
     */
    public static double doubleMul(String a,String b){
        if(StringUtils.isBlank(a) || StringUtils.isBlank(b)){
            throw new RuntimeException("入参不可为null或者空");
        }

        BigDecimal a1 = new BigDecimal(a);
        BigDecimal aa = new BigDecimal(b);
        return  a1.multiply(aa).doubleValue();
    }

    /**
     * double乘法
     */
    public static double doubleMul(Double a,Double b){
        if(a == null || b == null){
            throw new RuntimeException("入参不可为null");
        }
        return doubleMul(String.valueOf(a),String.valueOf(b));
    }

    /**
     * double 除法
     * @return
     */
    public static double doubleDivide(String a,String b){
        if(StringUtils.isBlank(a) || StringUtils.isBlank(b)){
            throw new RuntimeException("入参不可为null或者空");
        }

        BigDecimal bd7 = new BigDecimal(a);
        BigDecimal bd8 = new BigDecimal(b);
        BigDecimal bigDecimal=bd7.divide(bd8,5,BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    /**
     * double 除法
     * @return
     */
    public static double doubleDivide(Double a,Double b){
        if(a == null || b == null){
            throw new RuntimeException("入参不可为null");
        }
        return doubleDivide(String.valueOf(a),String.valueOf(b));
    }


    public static void random(int n, int L){
        Random rand = new Random();
        int temp = L;
        for(int i = 0, j; i < n-1; i++){
            j = rand.nextInt(temp-1) + 1;
            temp -= j;
            System.out.println(j);
        }
        System.out.println(temp);
    }

    public static void main(String[] args) {
        Double a = null;
        System.out.println(doubleAdd(3d,0d));
    }

}
