package com.yuan.loveboot.poop.service;

import com.yuan.loveboot.mybatis.service.BaseService;
import com.yuan.loveboot.poop.po.PoopLog;
import com.yuan.loveboot.poop.po.PoopSummary;
import com.yuan.loveboot.poop.vo.PoopLogVO;
import com.yuan.loveboot.utils.PageDTO;
import com.yuan.loveboot.utils.PageVO;
import com.yuan.loveboot.utils.Result;
import com.yuan.loveboot.utils.YearMonthRange;

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
     * 保存便便记录
     *
     * @param type 便便类型
     */
    void save(int type);
}
