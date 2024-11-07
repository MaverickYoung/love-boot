package com.yuan.loveboot.properties;

/**
 * 安全配置项
 *
 * @author Maverick
 */
public class SecurityProperties {
    /**
     * 访问令牌 过期时间(单位：秒)，默认2小时
     */
    public static final int ACCESS_TOKEN_EXPIRE = 60 * 60 * 2;
    /**
     * 刷新令牌 过期时间(单位：秒)，默认14天
     */
    public static final int REFRESH_TOKEN_EXPIRE = 60 * 60 * 24 * 14;

    /**
     * 验证码 过期时间(单位：秒)，默认60秒
     */
    public static final int CAPTCHA_EXPIRE = 60;
}
