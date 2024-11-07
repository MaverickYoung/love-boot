package com.yuan.loveboot.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 查询公共参数
 *
 * @author Maverick
 */
@Data
public class Query {
    @NotNull(message = "页码不能为空")
    @Min(value = 1, message = "页码最小值为 1")
    @Schema(description = "当前页码")
    Integer page;

    @NotNull(message = "每页条数不能为空")
    @Min(value = 1, message = "每页条数不小于1")
    @Max(value = 1000, message = "每页条数不大于1000")
    @Schema(description = "每页条数")
    Integer limit;

    @Schema(description = "排序字段")
    String order;

    @Schema(description = "是否升序")
    boolean asc;
}
