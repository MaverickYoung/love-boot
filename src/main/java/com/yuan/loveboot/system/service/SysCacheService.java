package com.yuan.loveboot.system.service;

/**
 * 系统缓存
 *
 * @author Maverick
 */
public interface SysCacheService {
    /**
     * 缓存 访问令牌
     *
     * @param userId      用户ID
     * @param accessToken 访问令牌
     */
    void cacheAccessToken(int userId, String accessToken);

    /**
     * 通过请求头中的 访问令牌 获取用户ID
     *
     * @return 用户ID
     */
    Integer getUserId();

    /**
     * 删除 访问令牌 缓存
     *
     * @param userId 用户ID
     */
    void deleteAccessToken(int userId);

    /**
     * 缓存 验证码
     *
     * @param key   key
     * @param value 值
     */
    void cacheCaptcha(String key, String value);

    /**
     * 通过 验证码key 获取值
     *
     * @param key key
     * @return 值
     */
    String getCaptcha(String key);

    /**
     * 删除 验证码 缓存
     *
     * @param key key
     */
    void deleteCaptcha(String key);
}
