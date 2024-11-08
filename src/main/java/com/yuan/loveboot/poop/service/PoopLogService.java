package com.yuan.loveboot.poop.service;

import com.yuan.loveboot.mybatis.service.BaseService;
import com.yuan.loveboot.poop.po.PoopLog;
import com.yuan.loveboot.poop.po.PoopSummary;

import java.time.YearMonth;
import java.util.List;

/**
 * 便便记录
 *
 * @author Maverick
 */
public interface PoopLogService extends BaseService<PoopLog> {
    /**
     * 按月统计用户便便数量
     *
     * @param startMonth 统计起始月份
     * @param endMonth   统计结束月份
     * @return 统计结果
     */
    List<PoopSummary> summaryList(YearMonth startMonth, YearMonth endMonth);

    /**
     * 统计上月用户便便数量
     *
     * @return 统计结果
     */
    List<PoopSummary> summaryList();
}
