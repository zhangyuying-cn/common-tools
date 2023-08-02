package com.zyy.tools.enums;

/**
 * 性别枚举
 */
public enum SexEnum {

    MAN(1, "男"),
    WOMAN(2, "女");

    private final Integer code;
    private final String name;

    SexEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}