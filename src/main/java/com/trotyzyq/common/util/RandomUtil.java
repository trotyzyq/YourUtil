package com.trotyzyq.common.util;

import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *  random工具类
 */
public class RandomUtil {
    /**
     * 将一个整数L随机拆分成n个整数,对数据不做校验，controller进行校验
     * @param n
     * @param l
     * @return
     */
    public static List<Integer> randomInt(int n, int l){
        List<Integer> list = new ArrayList<>();
        Random rand = new Random();
        int temp = l;
        for(int i = 0, j; i < n-1; i++){
            j = rand.nextInt(temp-1) + 1;
            temp -= j;
            list.add(j);
        }
        return list;
    }

    /**
     * 指定的整数 随机生成 numberCount 个
     * @param numberCount 数量
     * @param goal 目标
     * @return
     */
    public static List<Integer> randomListInt(int numberCount, int goal){
        List<Integer> list = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0, j; i < numberCount; i++){
            j = rand.nextInt(goal) + 1;
            list.add(j);
        }
        return list;
    }

    /**
     * 产生一个随机数在这之间 左包右不包，和substring一样
     * @param start 开始
     * @param end 结束（不包括）
     * @return
     */
    public static int nextInt(int start,int end){
        if(start < 0){
            start = 0;
        }
        Random random = new Random();
        int num = random.nextInt(end);
        while(num < start){
            num = random.nextInt(end);
        }
        return num;
    }

    /**
     * 产生一个随机数0-end,不包含end
     * @param end 结束（不包括）
     * @return int
     */
    public static int nextInt(int end){
        Random random = new Random();
        return random.nextInt(end);
    }

    public static void main(String[] args) {
        for(int i=0;i< 100000;i++){
            System.out.println(nextInt(3,10));
        }
    }
}
