package com.zhangyuying.tools.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 提供使用BigDecimal进行精确计算的工具类
 */
public class BigDecimalUtil {

    /**
     * 默认除法运算精度
     */
    private static final int DEF_DIV_SCALE = 10;

    /**
     * 四舍五入的分母
     */
    private static final BigDecimal ROUNDING_DENOMINATOR = BigDecimal.valueOf(1);

    /**
     * 禁止实例化
     */
    private BigDecimalUtil() {
    }

    /**
     * 提供精确的加法运算。
     *
     * @param v1 被加数
     * @param v2 加数
     * @return 两个参数的和
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.add(b2).doubleValue();
    }

    public static double add(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return rounding(add(v1, v2), scale);
    }

    public static double add(double v1, double... v2List) {
        for (double v2 : v2List) {
            v1 = add(v1, v2);
        }
        return v1;
    }

    public static float add(float v1, float v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.add(b2).floatValue();
    }

    /**
     * 提供精确的减法运算。
     *
     * @param v1 被减数
     * @param v2 减数
     * @return 两个参数的差
     */
    public static double subtract(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.subtract(b2).doubleValue();
    }

    public static double subtract(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return rounding(subtract(v1, v2), scale);
    }

    public static double subtract(double v1, double... v2List) {
        for (double v2 : v2List) {
            v1 = subtract(v1, v2);
        }
        return v1;
    }

    public static float subtract(float v1, float v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.subtract(b2).floatValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double multiply(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.multiply(b2).doubleValue();
    }

    public static double multiply(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        return rounding(multiply(v1, v2), scale);
    }

    public static double multiply(double v1, double... v2List) {
        for (double v2 : v2List) {
            v1 = multiply(v1, v2);
        }
        return v1;
    }

    public static float multiply(float v1, float v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.multiply(b2).floatValue();
    }

    /**
     * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到
     * 小数点以后10位，以后的数字四舍五入。
     *
     * @param v1 被除数
     * @param v2 除数
     * @return 两个参数的商
     */
    public static double divide(double v1, double v2) {
        return divide(v1, v2, DEF_DIV_SCALE);
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double divide(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.divide(b2, scale, RoundingMode.HALF_UP).doubleValue();
    }

    public static float divide(float v1, float v2, int scale) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.divide(b2, scale, RoundingMode.HALF_UP).floatValue();
    }


    /**
     * 精确的小数位四舍五入处理。
     *
     * @param v     值
     * @param scale 保留位
     * @return 四舍五入后的结果
     */
    public static double rounding(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = BigDecimal.valueOf(v);
        return b.divide(ROUNDING_DENOMINATOR, scale, RoundingMode.HALF_UP).doubleValue();
    }

    /**
     * 精确的小数位进位处理。
     *
     * @param v            值
     * @param scale        保留位
     * @param roundingMode 进位方式
     * @return 进位后的结果
     */
    public static double rounding(double v, int scale, RoundingMode roundingMode) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = BigDecimal.valueOf(v);
        return b.divide(ROUNDING_DENOMINATOR, scale, roundingMode).doubleValue();
    }

    /**
     * 精确对比两个数字
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
     */
    public static int compareTo(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.compareTo(b2);
    }

    /**
     * 精确对比两个数字
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
     */
    public static int compareTo(float v1, float v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.compareTo(b2);
    }

    /**
     * 精确对比两个数字
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 是否相等
     */
    public static boolean equals(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.compareTo(b2) == 0;
    }

    /**
     * 精确对比两个数字
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 是否相等
     */
    public static boolean equals(float v1, float v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.compareTo(b2) == 0;
    }

    /**
     * 返回最大的一个值
     *
     * @param vList 需要被对比的数
     * @return 返回两个数中大的一个值
     */
    public static double returnMax(double... vList) {
        if (vList.length == 0) {
            throw new IllegalArgumentException("The vList length must be not zero");
        }
        BigDecimal result = BigDecimal.valueOf(vList[0]);
        for (double v : vList) {
            BigDecimal b = BigDecimal.valueOf(v);
            result = result.max(b);
        }
        return result.doubleValue();
    }

    /**
     * 返回最小的一个值
     *
     * @param vList 需要被对比的数
     * @return 返回两个数中小的一个值
     */
    public static double returnMin(double... vList) {
        if (vList.length == 0) {
            throw new IllegalArgumentException("The vList length must be not zero");
        }
        BigDecimal result = BigDecimal.valueOf(vList[0]);
        for (double v : vList) {
            BigDecimal b = BigDecimal.valueOf(v);
            result = result.min(b);
        }
        return result.doubleValue();
    }

    /**
     * 转化为float
     *
     * @param v double类型的值
     * @return float类型的值
     */
    public static double toFloat(double v) {
        return BigDecimal.valueOf(v).floatValue();
    }

    /**
     * 转化为double
     *
     * @param v float类型的值
     * @return double类型的值
     */
    public static double toDouble(Float v) {
        return BigDecimal.valueOf(v).doubleValue();
    }


}
