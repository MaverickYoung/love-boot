package com.yuan.loveboot.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 刷新令牌
 *
 * @author Maverick
 */
@Data
@Schema(description = "账号登录")
public class SysTokenDTO implements Serializable {
    private String refreshToken;
}
