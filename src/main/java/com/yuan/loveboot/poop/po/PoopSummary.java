package com.yuan.loveboot.poop.po;

import com.yuan.loveboot.common.mybatis.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 按月统计用户便便的核算表
 *
 * @author Maverick
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PoopSummary extends BasePO {
    /**
     * 统计月份，格式为 YYYY-MM
     */
    private String month;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户当月的便便数量
     */
    private Integer poopCount;

    /**
     * 是否为当月冠军
     */
    private Boolean winnerStatus;

    /**
     * 是否领取奖品
     */
    private Boolean rewardStatus;

    /**
     * 奖品图片名称
     */
    private String rewardImage;
}
