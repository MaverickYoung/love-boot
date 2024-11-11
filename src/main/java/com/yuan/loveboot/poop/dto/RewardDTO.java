package com.yuan.loveboot.poop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 更新奖励
 *
 * @author Maverick
 */
@Data
public class RewardDTO {
    @NotBlank(message = "图片不能为空")
    private String image;

    @NotBlank(message = "月份不能为空")
    private String month;
}
