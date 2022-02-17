package com.qiyuesuotest.sdk.util;

/**
 * @author: Coder_Jarvis
 * @description:
 * @date: 2022-02-2022/2/16-13:50
 * @version: 1.0
 */
public class StringUtil {
    public static boolean emptyOrNull(String str) {
        return "".equals(str) || null == str;
    }
}
