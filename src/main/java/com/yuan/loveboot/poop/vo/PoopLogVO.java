package com.yuan.loveboot.poop.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yuan.loveboot.common.utils.DateUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 便便记录
 *
 * @author Maverick
 */
@Data
@Schema(description = "便便记录")
public class PoopLogVO {

    @Schema(description = "ID")
    private int id;

    @Schema(description = "用户ID")
    private int userId;

    @Schema(description = "便便类型")
    private int poopType;

    @Schema(description = "记录时间")
    @JsonFormat(pattern = DateUtil.DATE_TIME_PATTERN)
    private LocalDateTime logTime;
}
