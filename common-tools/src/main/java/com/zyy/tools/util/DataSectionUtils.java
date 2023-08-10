package com.zyy.tools.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class DataSectionUtils {

    private static final int SECTION = 1000;

    /**
     * 数据分片处理
     * 查询参数过大导致sql过慢，可分片查询，一次查询1000
     *
     * @param function 查询方法
     * @param params   查询参数
     * @return 全部结果数据
     */
    public static List dataSection(Function<List, List> function, List params) {
        int count = params.size() / SECTION + 1;
        List result = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int toIndex = (i + 1) * SECTION;
            if (i + 1 == count) {
                toIndex = params.size();
            }
            List partParams = params.subList(i * SECTION, toIndex);
            if (CollectionUtils.isNotEmpty(partParams)) {
                result.addAll(function.apply(partParams));
            }
        }
        return result;
    }
}
