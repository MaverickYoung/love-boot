package com.yuan.loveboot.poop.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 便便核算
 *
 * @author Maverick
 */
@Data
@Schema(description = "便便核算")
public class PoopSummaryVO {
    @Schema(description = "统计月份，格式为 YYYY-MM")
    private String month;

    @Schema(description = "用户ID")
    private int userId;

    @Schema(description = "便便数量")
    private int poopCount;

    @Schema(description = "是否是冠军")
    private boolean isWinner;
}
