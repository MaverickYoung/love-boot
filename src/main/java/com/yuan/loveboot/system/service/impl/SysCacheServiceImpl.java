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

import java.util.Objects;

/**
 * @author Maverick
 */
@Service
@RequiredArgsConstructor
public class SysCacheServiceImpl implements SysCacheService {
    private final CacheManager cacheManager;
    private final HttpServletRequest request;

    @Override
    public void cacheAccessToken(int userId, String accessToken) {
        Objects.requireNonNull(cacheManager.getCache(CacheConfig.ACCESS_TOKEN_KEY)).put(accessToken, userId);
    }

    @Override
    public Integer getUserId() {
        String accessToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        return getUserId(accessToken);
    }

    @Override
    public Integer getUserId(String accessToken) {
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
