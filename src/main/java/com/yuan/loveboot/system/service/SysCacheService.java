package com.yuan.loveboot.system.service;

/**
 * 系统缓存
 *
 * @author Maverick
 */
public interface SysCacheService {
    /**
     * 缓存AccessToken
     *
     * @param userId      用户ID
     * @param accessToken 访问令牌
     */
    void cacheAccessToken(Integer userId, String accessToken);

    /**
     * 通过请求头中的AccessToken获取用户ID
     *
     * @return 用户ID
     */
    Integer getUserId();

    /**
     * 删除AccessToken缓存
     *
     * @param userId 用户ID
     */
    void deleteAccessToken(Integer userId);

    /**
     * 缓存验证码
     *
     * @param key   key
     * @param value 值
     */
    void cacheCaptcha(String key, String value);

    /**
     * 通过验证码key获取值
     *
     * @param key key
     * @return 值
     */
    String getCaptcha(String key);

    /**
     * 删除验证码缓存
     *
     * @param key key
     */
    void deleteCaptcha(String key);
}
