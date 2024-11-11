package com.yuan.loveboot.poop.dao;

import com.yuan.loveboot.mybatis.dao.BaseDao;
import com.yuan.loveboot.poop.po.PoopLog;
import com.yuan.loveboot.poop.po.PoopSummary;
import com.yuan.loveboot.utils.YearMonthRange;
import org.apache.ibatis.annotations.Mapper;

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
    List<PoopSummary> countUserPoopMonthly(YearMonthRange range);
}
