package com.yuan.loveboot.poop.service;

import com.yuan.loveboot.mybatis.service.BaseService;
import com.yuan.loveboot.poop.po.PoopSummary;

import java.util.List;

/**
 * 便便核算
 *
 * @author Maverick
 */
public interface PoopSummaryService extends BaseService<PoopSummary> {
    /**
     * 按月统计用户便便日志数量，并插入或更新统计记录。
     */
    void calculateMonthlyPoopCount();

    /**
     * 更新当月冠军用户标记。
     */
    void updateMonthlyWinners();

    /**
     * 查询指定月份的所有冠军用户。
     *
     * @param month 年月（格式：YYYY-MM）
     * @return 冠军用户列表
     */
    List<PoopSummary> findMonthlyWinners(String month);

    /**
     * 查询指定用户在某个月的统计信息。
     *
     * @param userId 用户 ID
     * @param month  年月（格式：YYYY-MM）
     * @return 用户的统计信息
     */
    PoopSummary findUserSummaryByMonth(int userId, String month);

    /**
     * 更新用户的奖品领取状态。
     *
     * @param userId 用户 ID
     * @param month  年月（格式：YYYY-MM）
     */
    void updateRewardStatus(int userId, String month);
}
