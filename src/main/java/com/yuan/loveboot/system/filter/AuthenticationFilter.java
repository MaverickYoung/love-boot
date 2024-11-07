package com.yuan.loveboot.system.filter;

import com.yuan.loveboot.config.PermitResource;
import com.yuan.loveboot.enums.ResponseCode;
import com.yuan.loveboot.system.service.SysCacheService;
import com.yuan.loveboot.utils.JsonUtil;
import com.yuan.loveboot.utils.Result;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * 鉴权过滤器
 *
 * @author Maverick
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final PermitResource permitResource;
    private final SysCacheService sysCacheService;
    private final AntPathMatcher antPathMatcher;

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        List<String> permitList = permitResource.getPermitList();
        // 检查是否在白名单中
        for (String permitPath : permitList) {
            if (antPathMatcher.match(permitPath, requestURI)) {
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 获取 Authorization 头
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isBlank(token)) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonUtil.toJsonString(Result.error(ResponseCode.UNAUTHORIZED)));
            return;
        }

        // 验证 token 的有效性
        Integer userId = sysCacheService.getUserId();
        if (userId == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonUtil.toJsonString(Result.error(ResponseCode.ACCESS_TOKEN_INVALID)));
            return;
        }

        // 如果验证通过，继续处理请求
        filterChain.doFilter(request, response);
    }
}
