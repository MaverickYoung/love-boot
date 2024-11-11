package com.yuan.loveboot.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.YearMonth;

/**
 * 年月范围
 *
 * @author Maverick
 */
@Data
@AllArgsConstructor
public class YearMonthRange {

    @Schema(description = "起始年月 YYYY-MM")
    private YearMonth start;

    @Schema(description = "结束年月 YYYY-MM")
    private YearMonth end;

    /**
     * 构造函数，接收起始和结束年月
     *
     * @param startMonth 起始年月 YYYY-MM
     * @param endMonth   结束年月 YYYY-MM
     */
    public YearMonthRange(String startMonth, String endMonth) {
        this.start = YearMonth.parse(startMonth);
        this.end = YearMonth.parse(endMonth);
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