package com.yuan.loveboot.common.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.yuan.loveboot.common.properties.SecurityProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 缓存配置
 *
 * @author Maverick
 */
@Configuration
@EnableCaching
public class CacheConfig {
    public static final String ACCESS_TOKEN_KEY = "accessToken";
    public static final String CAPTCHA_KET = "captcha";

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        // 设置 访问令牌 缓存
        cacheManager.registerCustomCache(ACCESS_TOKEN_KEY, Caffeine.newBuilder()
                .expireAfterWrite(SecurityProperties.ACCESS_TOKEN_EXPIRE, TimeUnit.SECONDS)
                .maximumSize(100).build());

        // 设置 验证码 缓存
        cacheManager.registerCustomCache(CAPTCHA_KET, Caffeine.newBuilder()
                .expireAfterWrite(SecurityProperties.CAPTCHA_EXPIRE, TimeUnit.SECONDS)
                .maximumSize(50).build());

        return cacheManager;
    }
}
