package com.yuan.loveboot.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 跨域配置
 *
 * @author Maverick
 */
@Configuration
public class CorsConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration corsConfiguration = new CorsConfiguration();

        // 允许携带 Cookie
        corsConfiguration.setAllowCredentials(true);

        // 允许所有头部
        corsConfiguration.addAllowedHeader("*");

        // 允许所有来源的请求
        corsConfiguration.addAllowedOriginPattern("*");

        // 允许所有 HTTP 方法
        corsConfiguration.addAllowedMethod("*");

        // 将配置应用到所有路径
        source.registerCorsConfiguration("/**", corsConfiguration);

        // 使用 FilterRegistrationBean 注册 CorsFilter，并设置优先级
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>(new CorsFilter(source));

        // 设置跨域过滤器的优先级为最低，确保它最先执行
        registrationBean.setOrder(Integer.MIN_VALUE);

        return registrationBean;
    }
}
