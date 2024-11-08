package com.yuan.loveboot.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户头像
 *
 * @author Maverick
 */
@Data
@Schema(description = "用户头像")
public class SysUserAvatarDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -5156974101004685754L;

    @NotBlank(message = "头像不能为空")
    @Schema(description = "头像")
    private String avatar;

}
