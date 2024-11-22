package com.yuan.loveboot.poop.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yuan.loveboot.common.mybatis.service.impl.BaseServiceImpl;
import com.yuan.loveboot.common.utils.PageDTO;
import com.yuan.loveboot.common.utils.PageVO;
import com.yuan.loveboot.common.utils.Result;
import com.yuan.loveboot.common.utils.YearMonthRange;
import com.yuan.loveboot.poop.converter.PoopLogConverter;
import com.yuan.loveboot.poop.dao.PoopLogDao;
import com.yuan.loveboot.poop.po.PoopLog;
import com.yuan.loveboot.poop.po.PoopSummary;
import com.yuan.loveboot.poop.service.PoopLogService;
import com.yuan.loveboot.poop.vo.PoopLogVO;
import com.yuan.loveboot.poop.vo.PoopStatsVO;
import com.yuan.loveboot.system.service.SysCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * 便便记录
 *
 * @author Maverick
 */
@Service
@RequiredArgsConstructor
public class PoopLogServiceImpl extends BaseServiceImpl<PoopLogDao, PoopLog> implements PoopLogService {
    private final SysCacheService sysCacheService;

    @Override
    public List<PoopSummary> countByMonth(YearMonthRange range) {
        return baseMapper.selectMonthlySummary(range);
    }

    @Override
    public List<PoopStatsVO> fetchStats(YearMonthRange range) {
        return baseMapper.selectMonthlyStats(range);
    }

    @Override
    public List<String> findAvailableMonths() {
        List<YearMonth> yearMonths = baseMapper.selectDistinctMonths();
        List<String> months = new ArrayList<>();
        for (YearMonth yearMonth : yearMonths) {
            months.add(yearMonth.getMonth().toString());
        }
        return months;
    }

    @Override
    public Result<PageVO<PoopLogVO>> page(PageDTO dto) {
        IPage<PoopLog> page = baseMapper.selectPage(getPage(dto), null);
        List<PoopLog> records = page.getRecords();
        List<PoopLogVO> list = PoopLogConverter.INSTANCE.convert(records);

        return Result.ok(new PageVO<>(list, page.getTotal()));
    }

    @Override
    public void save(int type) {
        PoopLog poopLog = new PoopLog();
        poopLog.setPoopType(type);
        poopLog.setUserId(sysCacheService.getUserId());
        poopLog.setLogTime(LocalDateTime.now());
        baseMapper.insert(poopLog);
    }
}
