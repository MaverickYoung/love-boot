package com.yuan.loveboot.system.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户Token
 *
 * @author Maverick
 */
@Data
@Schema(description = "用户Token")
public class SysUserTokenVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4246693017782474565L;

    @Schema(description = "access_token")
    @JsonProperty(value = "access_token")
    private String accessToken;

    @Schema(description = "refresh_token")
    @JsonProperty(value = "refresh_token")
    private String refreshToken;
}
