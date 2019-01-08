package com.trotyzyq.common.util;


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
    public static List<Double> getRandomList(double sum,int size){
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
     * @param a
     * @param b
     * @return
     */
    public static  double doubleAdd(double a,double b){
        BigDecimal bd7 = new BigDecimal(String.valueOf(a));
        BigDecimal bd8 = new BigDecimal(String.valueOf(b));
        return bd7.add(bd8).doubleValue();
    }

    /**Double的加**/
    public static  double doubleAdd(double a,double b,double c){
        return doubleAdd(doubleAdd(a,b),c);
    }



    /**
     * double 相减
     */
    public static double doubleSub(double d1,double d2){
        String d3=Double.toString(d1);
        String d4=Double.toString(d2);
        BigDecimal bd1 = new BigDecimal(d3);
        BigDecimal bd2 = new BigDecimal(d4);
        return bd1.subtract(bd2).doubleValue();
    }

    /**
     * double乘法
     * @param d1
     * @param d2
     * @return
     */
    public static double doubleMul(double d1,double d2){
        String d3=Double.toString(d1);
        String d4=Double.toString(d2);
        BigDecimal a1 = new BigDecimal(d3);
        BigDecimal aa = new BigDecimal(d4);
        double dd=a1.multiply(aa).doubleValue();
        return dd;
    }

    /**
     * double 除法
     * @param d1 double
     * @param d2
     * @return
     */
    public static double doubleDivide(double d1,double d2){
        String d3=Double.toString(d1);
        String d4=Double.toString(d2);
        BigDecimal bd7 = new BigDecimal(d3);
        BigDecimal bd8 = new BigDecimal(d4);
        BigDecimal bigDecimal=bd7.divide(bd8,5,BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
    }

    /**
     * double 除法
     * @param d1 String
     * @param d2
     * @return
     */
    public static double doubleDivide(String d1,String d2){
        BigDecimal bd7 = new BigDecimal(d1);
        BigDecimal bd8 = new BigDecimal(d2);
        BigDecimal bigDecimal=bd7.divide(bd8,5,BigDecimal.ROUND_HALF_UP);
        return bigDecimal.doubleValue();
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
        random(1,7);
    }

}
