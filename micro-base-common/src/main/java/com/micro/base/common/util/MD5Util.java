package com.micro.base.common.util;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {

    private static final String KEY = "micro-service";

    public static String md5(String text) {
        return DigestUtils.md5Hex(text + KEY);
    }

    public static Boolean verify(String text, String md5) {
        return md5(text).equals(md5);
    }
}
