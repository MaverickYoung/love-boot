package com.yuan.loveboot.poop.po;

import com.yuan.loveboot.mybatis.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
     * 记录时间
     */
    private LocalDateTime logTime;
    /**
     * 便便类型
     */
    private Integer poopType;
}
