package com.yuan.loveboot.utils;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.YearMonth;

/**
 * 年月范围
 *
 * @author Maverick
 */
@Data
public class YearMonthRange {

    @Schema(description = "起始年月 YYYY-MM")
    private YearMonth startMonth;

    @Schema(description = "结束年月 YYYY-MM")
    private YearMonth endMonth;

    public YearMonthRange(String startMonth, String endMonth) {
        this.startMonth = YearMonth.parse(startMonth);
        this.endMonth = YearMonth.parse(endMonth);
    }

    public static void main(String[] args) {
        String startMonth1 = "2020";
        String startMonth2 = "2021-10";
        String startMonth3 = "2021-11";
    }
}
