package com.yuan.loveboot.poop.service;

import com.yuan.loveboot.mybatis.service.BaseService;
import com.yuan.loveboot.poop.po.PoopSummary;
import com.yuan.loveboot.poop.vo.UserStatsVO;
import com.yuan.loveboot.utils.YearMonthRange;

import java.time.YearMonth;
import java.util.List;

/**
 * 便便核算
 *
 * @author Maverick
 */
public interface PoopSummaryService extends BaseService<PoopSummary> {
    /**
     * 统计上月用户便便数量，并插入或更新统计记录。
     */
    void syncLastMonthPoopStatistics();

    /**
     * 更新上月冠军用户标记。
     */
    void updateLastMonthWinners();

    /**
     * 每月第一天自动执行上月用户便便统计并更新上月冠军用户标记。
     */
    void syncLastMonthPoopStatisticsScheduled();

    /**
     * 获取指定月份范围的用户的便便统计信息。
     *
     * @param range 查询范围
     * @return 统计结果
     */
    List<UserStatsVO> findPoopSummaryByMonth(YearMonthRange range);

    /**
     * 更新奖励领取状态和图片。
     *
     * @param imageBytes 奖励图片
     * @param month      年月（格式：YYYY-MM）
     */
    void updateReward(byte[] imageBytes, YearMonth month);
}
