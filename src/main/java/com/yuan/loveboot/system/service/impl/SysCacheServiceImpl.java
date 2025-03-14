package com.yuan.loveboot.system.service.impl;


import com.yuan.loveboot.common.config.CacheConfig;
import com.yuan.loveboot.system.service.SysCacheService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

/**
 * @author Maverick
 */
@Service
@RequiredArgsConstructor
public class SysCacheServiceImpl implements SysCacheService {
    private final CacheManager cacheManager;

    @Override
    public void cacheAccessToken(int userId, String accessToken) {
        Objects.requireNonNull(cacheManager.getCache(CacheConfig.ACCESS_TOKEN_KEY)).put(accessToken, userId);
    }

    @Override
    public Integer getUserId() {
        // 尝试从当前线程获取请求上下文
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            // 非 Web 请求环境下（如定时任务）返回默认值
            return null; // 或者返回一个特定的默认用户ID，如 0
        }
        HttpServletRequest request = ((ServletRequestAttributes) attributes).getRequest();
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(accessToken)) {
            return null;
        }

        // 从缓存中获取值
        Cache.ValueWrapper valueWrapper = Objects.requireNonNull(cacheManager.getCache(CacheConfig.ACCESS_TOKEN_KEY)).get(accessToken);
        if (valueWrapper == null) {
            return null;
        }

        // 返回缓存中的 userId
        return (Integer) valueWrapper.get();
    }


    @Override
    public void deleteAccessToken(int userId) {
        Objects.requireNonNull(cacheManager.getCache(CacheConfig.ACCESS_TOKEN_KEY)).evict(userId);
    }

    @Override
    public void cacheCaptcha(String key, String value) {
        Objects.requireNonNull(cacheManager.getCache(CacheConfig.CAPTCHA_KET)).put(key, value);
    }

    @Override
    public String getCaptcha(String key) {
        // 从缓存中获取值
        Cache.ValueWrapper valueWrapper = Objects.requireNonNull(cacheManager.getCache(CacheConfig.CAPTCHA_KET)).get(key);
        if (valueWrapper != null) {
            // 返回缓存中的 value
            return (String) valueWrapper.get();
        }
        // 如果缓存中没有值，返回 null
        return null;
    }

    @Override
    public void deleteCaptcha(String key) {
        Objects.requireNonNull(cacheManager.getCache(CacheConfig.CAPTCHA_KET)).evict(key);
    }
}
