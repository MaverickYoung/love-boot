package com.yuan.loveboot.common.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 分页
 *
 * @author Maverick
 */
@Data
@Schema(description = "分页数据")
public class PageVO<T>  implements Serializable {
    @Serial
    private static final long serialVersionUID = -1142397817018299779L;

    @Schema(description = "总记录数")
    private int total;

    @Schema(description = "列表数据")
    private List<T> list;

    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public PageVO(List<T> list, long total) {
        this.list = list;
        this.total = (int)total;
    }
}