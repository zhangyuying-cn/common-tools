package com.zyy.tools.util;

import java.util.UUID;

public class UuidUtil {
    public static String uuid32() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String uuid16() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 16);
    }

    public static String uuid8() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 8);
    }
}
