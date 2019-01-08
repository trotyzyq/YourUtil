package com.trotyzyq.common.util;

import java.util.Collection;
import java.util.Map;

/**
 * 任意对象判空
 * created by xushilong on 2018/8/6.
 */
public class ValidateUtil {

    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     * @param obj
     * @return  返回结果：true对象内容不为空;false 对象内容为空
     */
    public static boolean isNullOrEmpty(Object obj) {
        if (obj == null)
            return false;

        if (obj instanceof CharSequence) {
            if (obj instanceof String) {
                if (((String) obj).equalsIgnoreCase("null")) {
                    return false;
                }
            }
            return ((CharSequence) obj).length() > 0;
    }
        if (obj instanceof Collection) {
            return !((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return !((Map) obj).isEmpty();
        }
        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return false;
            }
            boolean empty = false;
            for (int i = 0; i < object.length; i++) {
                if (isNullOrEmpty(object[i])) {
                    empty = true;
                    break;
                }
            }
            return empty;
        }
        return true;
    }


    /**
     * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
     * @param obj
     * @return  返回结果：true对象内容为空;false 对象内容不为空
     */
    public static boolean isNull(Object obj) {
        if (obj == null)
            return true;

        if (obj instanceof CharSequence) {
            if (obj instanceof String) {
                if (((String) obj).equalsIgnoreCase("null")) {
                    return true;
                }
            }
            return ((CharSequence) obj).length() == 0;
        }
        if (obj instanceof Collection) {
            return ((Collection) obj).isEmpty();
        }
        if (obj instanceof Map) {
            return ((Map) obj).isEmpty();
        }
        if (obj instanceof Object[]) {
            Object[] object = (Object[]) obj;
            if (object.length == 0) {
                return true;
            }
            boolean empty = true;
            for (int i = 0; i < object.length; i++) {
                if (!isNullOrEmpty(object[i])) {
                    empty = false;
                    break;
                }
            }
            return empty;
        }
        return false;
    }
}
