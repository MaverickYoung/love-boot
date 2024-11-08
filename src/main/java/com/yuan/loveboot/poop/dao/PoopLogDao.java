package com.yuan.loveboot.poop.dao;

import com.yuan.loveboot.mybatis.dao.BaseDao;
import com.yuan.loveboot.poop.po.PoopLog;
import com.yuan.loveboot.poop.po.PoopSummary;
import org.apache.ibatis.annotations.Param;
import org.mapstruct.Mapper;

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
     * @param startMonth 统计起始月份
     * @param endMonth   统计结束月份
     * @return 统计结果
     */
    List<PoopSummary> summaryList(@Param("startMonth") YearMonth startMonth, @Param("endMonth") YearMonth endMonth);
}
