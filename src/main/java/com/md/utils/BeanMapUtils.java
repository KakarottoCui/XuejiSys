package com.md.utils;

import org.springframework.cglib.beans.BeanMap;

import java.util.HashMap;
import java.util.Map;

public class BeanMapUtils {
    /**
     * 将对象装换为map
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap();
        if (bean != null) {
            //
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }


    /**
     * 将对象转换为自定义的map
     * 对比第一个方法就是map中的每个键都+update，并且把对象的首字母大写
     * @param bean
     * @param <T>
     * @return
     */
    public static <T> Map<String, Object> beanToMapForUpdate(T bean) {
        Map<String, Object> map = new HashMap();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put("update"+upperFirstLatter(key+""),beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 首字母转换为大写
     * @param letter
     * @return
     */
    public static String upperFirstLatter(String letter){
        char[] chars = letter.toCharArray();
        if(chars[0]>='a' && chars[0]<='z'){
            chars[0] = (char) (chars[0]-32);
        }
        return new String(chars);
    }
}
