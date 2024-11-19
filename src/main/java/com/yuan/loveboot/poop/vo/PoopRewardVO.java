package com.yuan.loveboot.poop.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 便便奖励
 *
 * @author Maverick
 */
@Data
@Schema(description = "便便奖励")
public class PoopRewardVO {
    @Schema(description = "统计月份，格式为 YYYY-MM")
    private String month;

    @Schema(description = "用户ID")
    private int userId;

    @Schema(description = "是否领取奖品")
    private boolean isRewarded;

    @Schema(description = "奖励图片")
    private String rewardImage;
}
