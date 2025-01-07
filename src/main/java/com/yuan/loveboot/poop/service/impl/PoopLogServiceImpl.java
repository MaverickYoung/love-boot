package com.yuan.loveboot.poop.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.yuan.loveboot.common.exception.ServerException;
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
import com.yuan.loveboot.poop.service.PoopSseService;
import com.yuan.loveboot.poop.vo.PoopLogVO;
import com.yuan.loveboot.poop.vo.PoopStatsVO;
import com.yuan.loveboot.poop.vo.PoopUserStateVO;
import com.yuan.loveboot.system.service.SysCacheService;
import com.yuan.loveboot.system.service.SysUserGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 便便记录
 *
 * @author Maverick
 */
@Service
@RequiredArgsConstructor
public class PoopLogServiceImpl extends BaseServiceImpl<PoopLogDao, PoopLog> implements PoopLogService {
    private final SysCacheService sysCacheService;
    private final SysUserGroupService sysUserGroupService;
    private final PoopSseService poopSseService;


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
    public void stopPoop(int id) {
        PoopLog poopLog = baseMapper.selectById(id);

        if (!Objects.equals(poopLog.getUserId(), sysCacheService.getUserId())) {
            throw new ServerException("参数异常");
        }

        Duration duration = Duration.between(poopLog.getStartTime(), LocalDateTime.now());
        poopLog.setDuration(duration);

        baseMapper.updateById(poopLog);

        PoopUserStateVO stateVO = new PoopUserStateVO();
        stateVO.setPoop(false);
        sendMessage(stateVO);
    }

    public int startPoop(int type) {
        PoopLog poopLog = new PoopLog();
        LocalDateTime now = LocalDateTime.now();
        poopLog.setPoopType(type);
        poopLog.setUserId(sysCacheService.getUserId());
        poopLog.setStartTime(now);
        baseMapper.insert(poopLog);

        PoopUserStateVO stateVO = new PoopUserStateVO();
        stateVO.setStartTime(now);
        stateVO.setPoop(true);
        sendMessage(stateVO);

        return poopLog.getId();
    }

    /**
     * 向用户分组的另一人推送消息
     *
     * @param vo 用户便便状态
     */
    private void sendMessage(PoopUserStateVO vo) {
        Integer otherUserId = sysUserGroupService.selectOtherUserId();
        if (otherUserId != null) {
            poopSseService.sendMessage(otherUserId, vo);
        }
    }
}
