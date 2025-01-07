package com.yuan.loveboot.poop.service;

import com.yuan.loveboot.common.mybatis.service.BaseService;
import com.yuan.loveboot.common.utils.PageDTO;
import com.yuan.loveboot.common.utils.PageVO;
import com.yuan.loveboot.common.utils.Result;
import com.yuan.loveboot.common.utils.YearMonthRange;
import com.yuan.loveboot.poop.po.PoopLog;
import com.yuan.loveboot.poop.po.PoopSummary;
import com.yuan.loveboot.poop.vo.PoopLogVO;
import com.yuan.loveboot.poop.vo.PoopStatsVO;

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
     * @param range 查询范围
     * @return 统计结果
     */
    List<PoopSummary> countByMonth(YearMonthRange range);

    /**
     * 获取便便统计
     *
     * @param range 查询范围
     * @return 统计结果
     */
    List<PoopStatsVO> fetchStats(YearMonthRange range);

    /**
     * 查找可用月份
     *
     * @return 月份数组 YYYY-MM
     */
    List<String> findAvailableMonths();

    /**
     * 分页查找
     *
     * @param dto 分页查找参数
     * @return 查找结果
     */
    Result<PageVO<PoopLogVO>> page(PageDTO dto);


    /**
     * 结束便便
     *
     * @param id 便便记录 ID
     */
    void stopPoop(int id);

    /**
     * 开始便便
     *
     * @param type 便便类型
     * @return id
     */
    int startPoop(int type);
}
