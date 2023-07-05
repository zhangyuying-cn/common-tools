package com.zyy.tools.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.NumberUtil;
import com.zyy.tools.enums.WeekEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class DateUtils extends DateUtil {


    /**
     * 指定格式转换Date对象
     * 封装转换异常返回 null 来满足业务判断
     *
     * @param str     日期字符串
     * @param pattern 格式
     * @return Date对象
     */
    public static Date parseDate(String str, String pattern) {
        if (StringUtils.isBlank(str.trim())) {
            return null;
        }
        return parse(str, pattern).toJdkDate();
    }


    public static String getDateFormat(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 计算两个时间相差的小时数 (保留1位小数)
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return 0.0 小时
     */
    public static double getDifferHours(Date beginDate, Date endDate) {
        long second = between(beginDate, endDate, DateUnit.SECOND);
        return NumberUtil.div(second, 3600, 1);
    }

    /**
     * 计算两个时间相差的分钟数 (保留1位小数)
     *
     * @param beginDate 开始时间
     * @param endDate   结束时间
     * @return 0.0 分钟
     */
    public static double getDifferMinute(Date beginDate, Date endDate) {
        long ms = between(beginDate, endDate, DateUnit.MS);
        return NumberUtil.div(ms, 60000, 1);
    }


    /**
     * 获取日期的星期。失败返回 null
     *
     * @param date 日期
     * @return 星期枚举
     */
    public static WeekEnum getWeek(Date date) {
        if (date == null) {
            return null;
        }
        WeekEnum weekEnum;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        switch (weekNumber) {
            case 0:
                weekEnum = WeekEnum.SUNDAY;
                break;
            case 1:
                weekEnum = WeekEnum.MONDAY;
                break;
            case 2:
                weekEnum = WeekEnum.TUESDAY;
                break;
            case 3:
                weekEnum = WeekEnum.WEDNESDAY;
                break;
            case 4:
                weekEnum = WeekEnum.THURSDAY;
                break;
            case 5:
                weekEnum = WeekEnum.FRIDAY;
                break;
            case 6:
                weekEnum = WeekEnum.SATURDAY;
                break;
            default:
                throw new IllegalArgumentException("不可识别的类型：weekNumber：{}" + weekNumber);
        }
        return weekEnum;
    }


    /**
     * 根据开始时间, 结束时间返回日期集合
     *
     * @param beginDate 开始日期
     * @param endDate   结束日期
     * @return 日期List
     */
    public static List<Date> differentDatesByDate(Date beginDate, Date endDate) {
        List<Date> dates = new ArrayList<>();
        beginDate = DateUtils.beginOfDay(beginDate);
        endDate = DateUtils.beginOfDay(endDate);
        long day = betweenDay(beginDate, endDate, true);
        if (day == 0) {
            // 当天
            dates.add(beginDate);
            return dates;
        } else {
            // 跨天
            for (int i = 0; i <= day; i++) {
                dates.add(beginDate);
                beginDate = offset(beginDate, DateField.DAY_OF_YEAR, 1).toJdkDate();
            }
        }
        return dates;
    }

    /**
     * 根据年,周获取开始与结束时间范围
     *
     * @param year 年份
     * @param week 某周
     * @return 时间范围 ["yyyy-MM-dd 00:00:00","yyyy-MM-dd 23:59:59"]
     */
    public static Date[] rangeWeekDate(int year, int week) {
        Calendar cal = Calendar.getInstance();
        // 设置每周的开始日期
        cal.setFirstDayOfWeek(Calendar.MONDAY);

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);

        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date beginDate = beginOfDay(cal.getTime()).toJdkDate();

        cal.add(Calendar.DAY_OF_WEEK, 6);
        Date endDate = endOfDay(cal.getTime()).toJdkDate();

        return new Date[]{beginDate, endDate};
    }

    /**
     * 根据年,月获取开始与结束时间范围
     *
     * @param year  年份
     * @param month 某月
     * @return 时间范围 ["yyyy-MM-dd 00:00:00","yyyy-MM-dd 23:59:59"]
     */
    public static Date[] rangeMonthDate(int year, int month) {
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);

        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date beginDate = beginOfDay(cal.getTime()).toJdkDate();

        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        Date endDate = endOfDay(cal.getTime()).toJdkDate();

        return new Date[]{beginDate, endDate};
    }

    /**
     * 获取指定时分秒的日期
     *
     * @param date        日期对象
     * @param hour        指定小时
     * @param minute      指定分钟
     * @param second      指定秒
     * @param millisecond 指定毫秒
     * @return 日期对象
     */
    public static Date getAssignDay(Date date, int hour, int minute, int second, int millisecond) {
        if (null == date) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, millisecond);
        return calendar.getTime();
    }


    /**
     * 增加日期的分钟
     *
     * @param date   日期
     * @param amount 增加数量。可为负数
     * @return 增加分钟后的日期
     */
    public static Date addMinute(Date date, int amount) {
        return offset(date, DateField.MINUTE, amount).toJdkDate();
    }

    /**
     * 增加日期
     *
     * @param date   日期
     * @param amount 增加数量。可为负数
     * @return 增加分钟后的日期
     */
    public static Date addDay(Date date, int amount) {
        return offset(date, DateField.DAY_OF_YEAR, amount).toJdkDate();
    }


}
