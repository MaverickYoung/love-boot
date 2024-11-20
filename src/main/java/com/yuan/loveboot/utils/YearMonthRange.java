package com.yuan.loveboot.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.YearMonth;

/**
 * 年月范围
 *
 * @author Maverick
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class YearMonthRange {

    @Schema(description = "起始年月 YYYY-MM")
    private YearMonth start;

    @Schema(description = "结束年月 YYYY-MM")
    private YearMonth end;

    /**
     * 构造函数，接收起始和结束年月
     *
     * @param start 起始年月 YYYY-MM
     * @param end   结束年月 YYYY-MM
     */
    public YearMonthRange(String start, String end) {
        if (StringUtils.isEmpty(start)) {
            this.start = YearMonth.parse(start);
        }
        if (StringUtils.isEmpty(end)) {
            this.end = YearMonth.parse(end);
        }
    }

    /**
     * 构造函数，接收相同的起始和结束年月
     *
     * @param month 年月
     */
    public YearMonthRange(String month) {
        this(month, month);
    }

    /**
     * 构造函数，接收 YearMonth 类型的起始和结束年月
     *
     * @param month 年月
     */
    public YearMonthRange(YearMonth month) {
        this(month, month);
    }
}