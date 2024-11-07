package com.yuan.loveboot.system.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yuan.loveboot.utils.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 访问令牌
 *
 * @author Maverick
 */
@Data
@Schema(description = "访问令牌")
public class AccessTokenVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4028152326225735398L;

    @Schema(description = "accessToken")
    @JsonProperty(value = "accessToken")
    private String accessToken;

    @Schema(description = "accessToken 过期时间")
    @JsonFormat(pattern = DateUtil.DATE_TIME_PATTERN)
    private LocalDateTime accessTokenExpire;

}