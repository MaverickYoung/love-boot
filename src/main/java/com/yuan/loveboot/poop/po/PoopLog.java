package com.yuan.loveboot.poop.po;

import com.yuan.loveboot.common.mybatis.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * 便便记录
 *
 * @author Maverick
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PoopLog extends BasePO {
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 便便开始时间
     */
    private LocalDateTime startTime;
    /**
     * 便便时长
     */
    private Duration duration;
    /**
     * 便便类型
     */
    private Integer poopType;
}
