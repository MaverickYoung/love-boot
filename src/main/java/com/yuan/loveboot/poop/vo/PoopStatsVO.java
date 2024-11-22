package com.yuan.loveboot.poop.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 便便统计
 *
 * @author Maverick
 */
@Data
@Schema(description = "便便统计")
public class PoopStatsVO {
    @Schema(description = "统计月份，格式为 YYYY-MM")
    private String month;

    @Schema(description = "用户ID")
    private int userId;

    @Schema(description = "用户昵称")
    private String nickname;

    @Schema(description = "便便类型")
    private int poopType;

    @Schema(description = "便便数量")
    private int poopCount;
}
