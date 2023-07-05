package com.zyy.tools.enums;

/**
 * 周枚举
 */
public enum WeekEnum {

    MONDAY("星期一", "Monday", "周一", "Mon.", 1),
    TUESDAY("星期二", "Tuesday", "周二", "Tues.", 2),
    WEDNESDAY("星期三", "Wednesday", "周三", "Wed.", 3),
    THURSDAY("星期四", "Thursday", "周四", "Thur.", 4),
    FRIDAY("星期五", "Friday", "周五", "Fri.", 5),
    SATURDAY("星期六", "Saturday", "周六", "Sat.", 6),
    SUNDAY("星期日", "Sunday", "周日", "Sun.", 7);

    private final String cnName;
    private final String enName;
    private final String enShortName;
    private final String cnShortName;
    private final int number;

    WeekEnum(String cnName, String enName, String cnShortName, String enShortName, int number) {
        this.cnName = cnName;
        this.enName = enName;
        this.enShortName = enShortName;
        this.cnShortName = cnShortName;
        this.number = number;
    }


    public String getCnName() {
        return cnName;
    }

    public String getEnName() {
        return enName;
    }

    public String getEnShortName() {
        return enShortName;
    }

    public String getCnShortName() {
        return cnShortName;
    }

    public int getNumber() {
        return number;
    }
}