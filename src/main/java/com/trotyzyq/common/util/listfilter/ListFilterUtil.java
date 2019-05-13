package com.trotyzyq.common.util.listfilter;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;

public class ListFilterUtil {

    public static Boolean filterByField(List mainList, List filterList, String field){
        try {
            Set filterSet = new HashSet();
            for(Object filter : filterList){
                /** 设置可见 **/
                Field filterField = filter.getClass().getDeclaredField(field);
                filterField.setAccessible(true);
                /** 存入set**/
                filterSet.add(filterField.get(filter));
            }

            Iterator iterator = mainList.iterator();
            while (iterator.hasNext()){
                Object main = iterator.next();
                Field mainField = main.getClass().getDeclaredField(field);
                mainField.setAccessible(true);
                Object mainValue = mainField.get(main);
                if(filterSet.contains(mainValue)){
                    iterator.remove();
                }
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Test
    public void 测试过滤(){
        MainBean mainBean =new MainBean();
        mainBean.setShopId("123");
        MainBean mainBean2 =new MainBean();
        mainBean2.setShopId("1234");

        NeedFileterBean needFileterBean = new NeedFileterBean();
        needFileterBean.setShopId("123");

        List<MainBean> mainBeanList = new ArrayList<>();
        mainBeanList.add(mainBean);
        mainBeanList.add(mainBean2);
        List f = new ArrayList();
        f.add(needFileterBean);


        filterByField(mainBeanList, f,"shopId");
        System.out.println(JSON.toJSONString(mainBeanList));
        System.out.println(mainBeanList.size());
//
//        List<Integer> list = new ArrayList(2);
//        list.add(new Integer(1));
//        list.add(new Integer(2));
//        Iterator iterator = mainBeanList.iterator();
//        while (iterator.hasNext()){
//            Object iterator1 = iterator.next();
//            iterator.remove();
//        }
        System.out.println(mainBeanList.size());

    }
}
