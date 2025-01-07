package com.yuan.loveboot.poop.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户便便状态
 *
 * @author Maverick
 */
@Data
public class PoopUserStateVO {

    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 是否在便便
     */
    private boolean isPoop;
}
