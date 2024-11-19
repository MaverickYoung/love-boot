package com.yuan.loveboot.poop.dao;

import com.yuan.loveboot.mybatis.dao.BaseDao;
import com.yuan.loveboot.poop.po.PoopSummary;
import com.yuan.loveboot.poop.vo.PoopSummaryVO;
import com.yuan.loveboot.utils.YearMonthRange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.YearMonth;
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
     */
    List<Integer> selectUserWithMaxPoopCount(@Param("month") YearMonth month);

    /**
     * 获取指定月份范围的用户的便便统计信息
     *
     * @param range 查询范围
     * @return 统计结果
     */
    List<PoopSummaryVO> selectWithNicknameByMonth(YearMonthRange range);
}
