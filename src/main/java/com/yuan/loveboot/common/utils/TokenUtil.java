package com.yuan.loveboot.common.utils;

import java.util.UUID;

/**
 * @author Maverick
 */
public class TokenUtil {
    /**
     * 生成Token
     *
     * @return UUID除去“-”
     */
    public static String generate() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
