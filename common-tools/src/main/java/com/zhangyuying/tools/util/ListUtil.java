package com.zhangyuying.tools.util;

import com.zhangyuying.tools.constant.SymbolConstant;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * List工具类
 */
public class ListUtil<T> {

    private ListUtil() {
    }

    /**
     * 数组转换为List
     */
    public static <T> List<T> toList(T... array) {
        if (array == null) {
            return Collections.emptyList();
        }
        List<T> list = new ArrayList<>(array.length);
        Collections.addAll(list, array);
        return list;
    }

    /**
     * 字符串转换为List
     */
    public static List<String> toList(String str) {
        if (StringUtils.isBlank(str)) {
            return Collections.emptyList();
        }
        String[] array = str.split(SymbolConstant.COMMA);
        return toList(array);
    }

    /**
     * 根据指定符号，将字符串转换为List
     */
    public static List<String> toList(String str, String symbol) {
        if (StringUtils.isBlank(str)) {
            return Collections.emptyList();
        }
        String[] array = str.split(symbol);
        return toList(array);
    }

    public static String toString(List<String> list) {
        if(CollectionUtils.isEmpty(list)){
            return String
        }
        return String.join(SymbolConstant.COMMA, list);
    }


    public static <T> String toString(String... array) {
        return String.join(SymbolConstant.COMMA, array);
    }

}

