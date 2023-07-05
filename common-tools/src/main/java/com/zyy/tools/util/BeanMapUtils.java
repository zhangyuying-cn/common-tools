package com.zyy.tools.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Map<->Bean互转
 *
 * @author wangyong01
 */
public class BeanMapUtils {

    /**
     * 将对象属性转化为map集合
     * ignoreNullValue : 忽略null值
     */
    public static <T> Map<String, Object> beanToMap(T bean, boolean ignoreNullValue) {
        return BeanUtil.beanToMap(bean, new HashMap<>(), false, ignoreNullValue);
    }


    /**
     * 将对象属性转化为map集合
     * isToUnderlineCase：key转换为下划线格式
     * ignoreNullValue : 忽略null值
     */
    public static <T> Map<String, Object> beanToMap(T bean, boolean isToUnderlineCase, boolean ignoreNullValue) {
        return BeanUtil.beanToMap(bean, new HashMap<>(), isToUnderlineCase, ignoreNullValue);
    }

    /**
     * 将map集合中的数据转化为指定对象的同名属性中
     * ignoreNullValue : 忽略null值
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> clazz, boolean ignoreNullValue) {
        CopyOptions copyOptions = CopyOptions.create();
        copyOptions.setIgnoreNullValue(ignoreNullValue);
        return BeanUtil.mapToBean(map, clazz, true, copyOptions);
    }


}