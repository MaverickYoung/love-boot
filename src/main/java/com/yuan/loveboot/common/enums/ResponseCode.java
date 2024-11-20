package com.yuan.loveboot.common.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应编码枚举
 *
 * @author Maverick
 */
@Getter
@AllArgsConstructor
public enum ResponseCode {
    // 成功信息
    OK(1000, "操作成功"),

    // 用户权限相关
    UNAUTHORIZED(2001, "用户未授权，无法访问"),
    FORBIDDEN(2003, "权限不足，禁止访问"),

    // 认证（Token）相关
    ACCESS_TOKEN_INVALID(3001, "访问令牌失效"),
    REFRESH_TOKEN_INVALID(3002, "刷新令牌失效"),

    // 客户端校验类错误
    VALIDATION_ERROR(4000, "请求参数校验失败"),

    // 服务端错误
    INTERNAL_SERVER_ERROR(5000, "服务器异常，请稍后再试"),

    // 自定义或临时错误
    CUSTOM_ERROR(9000, "自定义错误，信息待定");

    @Schema(description = "响应编码")
    private final int code;

    @Schema(description = "消息内容")
    private final String msg;
}
