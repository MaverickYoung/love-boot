package com.yuan.loveboot.poop.dao;

import com.yuan.loveboot.common.mybatis.dao.BaseDao;
import com.yuan.loveboot.common.utils.YearMonthRange;
import com.yuan.loveboot.poop.po.PoopLog;
import com.yuan.loveboot.poop.po.PoopSummary;
import com.yuan.loveboot.poop.vo.PoopStatsVO;
import org.apache.ibatis.annotations.Mapper;

import java.time.YearMonth;
import java.util.List;

/**
 * 便便记录
 *
 * @author Maverick
 */
@Mapper
public interface PoopLogDao extends BaseDao<PoopLog> {
    /**
     * 按月统计用户便便数量
     *
     * @param range 查询范围
     * @return 统计结果
     */
    List<PoopSummary> selectMonthlySummary(YearMonthRange range);

    /**
     * 统计存在的月份
     *
     * @return 统计结果
     */
    List<YearMonth> selectDistinctMonths();

    /**
     * 按月与便便类型统计便便数量
     *
     * @param range 查询范围
     * @return 统计结果
     */
    List<PoopStatsVO> selectMonthlyStats(YearMonthRange range);
}
