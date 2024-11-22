package com.yuan.loveboot.poop.dao;

import com.yuan.loveboot.common.mybatis.dao.BaseDao;
import com.yuan.loveboot.poop.po.PoopSummary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 便便核算
 *
 * @author Maverick
 */
@Mapper
public interface PoopSummaryDao extends BaseDao<PoopSummary> {
    /**
     * 查询指定月份便便数量最多的用户
     *
     * @param month YYYY-MM
     */
    List<Integer> selectUserWithMaxPoopCount(@Param("month") String month);
}
