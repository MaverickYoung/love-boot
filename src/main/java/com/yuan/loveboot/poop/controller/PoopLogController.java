package com.yuan.loveboot.poop.controller;

import com.yuan.loveboot.common.utils.PageDTO;
import com.yuan.loveboot.common.utils.PageVO;
import com.yuan.loveboot.common.utils.Result;
import com.yuan.loveboot.common.utils.YearMonthRange;
import com.yuan.loveboot.poop.service.PoopLogService;
import com.yuan.loveboot.poop.vo.PoopLogVO;
import com.yuan.loveboot.poop.vo.PoopStatsVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 便便记录
 *
 * @author Maverick
 */
@RestController
@RequestMapping("poop/log")
@RequiredArgsConstructor
@Tag(name = "便便记录")
public class PoopLogController {
    private final PoopLogService poopLogService;

    @GetMapping("months")
    @Operation(summary = "获取可用月份")
    public Result<List<String>> getAvailableMonths() {
        return Result.ok(poopLogService.findAvailableMonths());
    }

    @GetMapping("page/{size}/{current}")
    @Operation(summary = "分页查询")
    public Result<PageVO<PoopLogVO>> page(
            @PathVariable @Min(value = 1, message = "页码最小值为 1") int current,
            @PathVariable @Min(value = 1, message = "每页条数不小于1") @Max(value = 1000, message = "每页条数不大于1000") int size
    ) {
        PageDTO dto = new PageDTO();
        dto.setSize(size);
        dto.setCurrent(current);
        dto.setAsc(true);
        dto.setOrder("log_time");
        return poopLogService.page(dto);
    }

    @GetMapping("list")
    @Operation(summary = "获取便便统计")
    public Result<List<PoopStatsVO>> getList(@Parameter(description = "起始年月 YYYY-MM") @RequestParam(required = false) String start,
                                             @Parameter(description = "结束年月 YYYY-MM") @RequestParam(required = false) String end) {
        YearMonthRange range = new YearMonthRange(start, end);
        List<PoopStatsVO> list = poopLogService.fetchStats(range);

        return Result.ok(list);
    }

    @PostMapping
    @Operation(summary = "开始便便")
    public Result<Integer> startPoop(@RequestParam int type) {
        int id = poopLogService.startPoop(type);

        return Result.ok(id);
    }
}
