package com.yuan.loveboot.system.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户令牌
 *
 * @author Maverick
 */
@Data
@Schema(description = "用户令牌")
public class SysUserTokenVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4246693017782474565L;

    @Schema(description = "访问令牌")
    @JsonProperty(value = "accessToken")
    private String accessToken;

    @Schema(description = "刷新令牌")
    @JsonProperty(value = "refreshToken")
    private String refreshToken;
}
