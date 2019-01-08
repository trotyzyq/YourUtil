package com.trotyzyq.common.util;

import com.trotyzyq.common.bo.Person;

import java.lang.reflect.Field;
import java.util.*;

/**
 *
 */
public class MergeUtil {

    /**
     * 合并list 按照字段
     * @param list
     * @param key 需要按照哪个字段
     * @param f1 需要操作的字段
     * @param f2 需要操作的字段
     * @return
     */
    public static void mergeList(List list, String key, String f1, String f2, String add) throws NoSuchFieldException, IllegalAccessException {
        /** 用来去重的set**/
        Set<String> set = new HashSet<>();
        List newList = new ArrayList();

        for(Object object : list){
            /** 旧对象 key 域**/
            Field keyField = object.getClass().getDeclaredField(key);
            keyField.setAccessible(true);

            /** 旧对象第一个域**/
            Field field1 = object.getClass().getDeclaredField(f1);
            field1.setAccessible(true);
            /** 旧对象第二个域**/
            Field field2 = object.getClass().getDeclaredField(f2);
            field2.setAccessible(true);

            /** key的值 **/
            String keyValue = (String) keyField.get(object);
            if(!set.contains(keyValue)){
                set.add(keyValue);
                newList.add(object);
            }else{
                for(Object newObject: newList){
                    Field newKeyField = newObject.getClass().getDeclaredField(key);
                    newKeyField.setAccessible(true);
                    String newKey = (String) newKeyField.get(newObject);
                    if(newKey.equals(keyValue)){
                        /** 获得新的对象的值**/
                       Field field3 = newObject.getClass().getDeclaredField(f1);
                       field3.setAccessible(true);

                       if(field3.getType() == Integer.class){
                           field3.set(newObject,(int)field3.get(newObject) + (int)field1.get(object));
                       }
                        if(field3.getType() == String.class){
                            field3.set(newObject,(String)field3.get(newObject) + add + (String)field1.get(object));
                        }
                        if(field3.getType() == Long.class){
                            field3.set(newObject,(long)field3.get(newObject) + (long)field1.get(object));
                        }
                        if(field3.getType() == Double.class){
                            field3.set(newObject,DoubleUtil.doubleAdd((double)field3.get(newObject), (double)field1.get(object))) ;
                        }

                       Field field4 = newObject.getClass().getDeclaredField(f2);
                       field4.setAccessible(true);
                        if(field4.getType() == Integer.class){
                            field4.set(newObject,(int)field4.get(newObject) + (int)field2.get(object));
                        }
                        if(field4.getType() == String.class){
                            field4.set(newObject,(String)field4.get(newObject) + add + (String)field2.get(object));
                        }
                        if(field4.getType() == Long.class){
                            field4.set(newObject,(long)field4.get(newObject) + (long)field2.get(object));
                        }
                        if(field4.getType() == Double.class){
                            field4.set(newObject,DoubleUtil.doubleAdd((double)field4.get(newObject), (double)field2.get(object))) ;
                        }
                    }
                }
            }
        }
        System.out.println(newList);
    }

    /**
     * 合并list 按照字段
     * @param list
     * @param key 需要按照哪个字段
     * @param f1 需要操作的字段
     * @return
     */
    public static List mergeList2(List list, String key, String f1, String add) throws NoSuchFieldException, IllegalAccessException {
        /** 用来去重的set**/
        Set<String> set = new HashSet<>();
        List newList = new ArrayList();

        for(Object object : list){
            /** 旧对象 key 域**/
            Field keyField = object.getClass().getDeclaredField(key);
            keyField.setAccessible(true);

            /** 旧对象第一个域**/
            Field field1 = object.getClass().getDeclaredField(f1);
            field1.setAccessible(true);

            /** key的值 **/
            String keyValue = (String) keyField.get(object);
            if(!set.contains(keyValue)){
                set.add(keyValue);
                newList.add(object);
            }else{
                for(Object newObject: newList){
                    Field newKeyField = newObject.getClass().getDeclaredField(key);
                    newKeyField.setAccessible(true);
                    String newKey = (String) newKeyField.get(newObject);
                    if(newKey.equals(keyValue)){
                        /** 获得新的对象的值**/
                        Field field3 = newObject.getClass().getDeclaredField(f1);
                        field3.setAccessible(true);

                        if(field3.getType() == Integer.class){
                            field3.set(newObject,(int)field3.get(newObject) + (int)field1.get(object));
                        }
                        if(field3.getType() == String.class){
                            field3.set(newObject,(String)field3.get(newObject) + add + (String)field1.get(object));
                        }
                        if(field3.getType() == Long.class){
                            field3.set(newObject,(long)field3.get(newObject) + (long)field1.get(object));
                        }
                        if(field3.getType() == Double.class){
                            field3.set(newObject,DoubleUtil.doubleAdd((double)field3.get(newObject), (double)field1.get(object))) ;
                        }
                    }
                }
            }
        }
        return list;
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        List<Person> peopleL = new ArrayList<>();
        Person p = new Person();

        p.setAge("我是");
        p.setName("zyq");
        p.setTime("我是");

        Person p1 = new Person();
        p1.setAge("大象");
        p1.setName("zyq");
        p1.setTime("大仙");

        Person p2 = new Person();
        p2.setAge("大象");
        p2.setName("zyq");
        p2.setTime("大仙");

        Person p3 = new Person();
        p3.setAge("大象");
        p3.setName("zyq1");
        p3.setTime("大仙");

        peopleL.add(p);
        peopleL.add(p1);
        peopleL.add(p2);
        peopleL.add(p3);

        mergeList2(peopleL,"name","age","、");
    }
}
