package com.yuan.loveboot.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 账号登录
 *
 * @author Maverick
 */
@Data
@Schema(description = "账号登录")
public class SysAccountLoginDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8387831028686366558L;

    @Schema(description = "用户名")
    @NotBlank(message = "用户名不为空")
    private String username;

    @Schema(description = "密码")
    @NotBlank(message = "密码不为空")
    private String password;

    @Schema(description = "唯一key")
    private String key;

    @Schema(description = "验证码")
    private String captcha;
}
