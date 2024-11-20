package com.yuan.loveboot.poop.controller;

import com.yuan.loveboot.poop.service.PoopSummaryService;
import com.yuan.loveboot.poop.vo.PoopRewardVO;
import com.yuan.loveboot.poop.vo.PoopSummaryVO;
import com.yuan.loveboot.utils.Result;
import com.yuan.loveboot.utils.YearMonthRange;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.YearMonth;
import java.util.List;

/**
 * 便便核算
 *
 * @author Maverick
 */
@RestController
@RequestMapping("poop/summary")
@RequiredArgsConstructor
@Tag(name = "便便核算")
public class PoopSummaryController {
    private final PoopSummaryService poopSummaryService;


    @GetMapping("list")
    @Operation(summary = "获取用户的便便核算信息")
    public Result<List<PoopSummaryVO>> getList(@RequestBody YearMonthRange range) {
        List<PoopSummaryVO> list = poopSummaryService.findByMonth(range);

        return Result.ok(list);
    }

    @PostMapping("reward")
    @Operation(summary = "更新奖励")
    public Result<String> updateReward(@Parameter(description = "奖励图片") @RequestParam("file") MultipartFile file,
                                       @Parameter(description = "月份") @RequestParam("month") String month) {
        poopSummaryService.updateReward(file, YearMonth.parse(month));

        return Result.ok();
    }

    @GetMapping("reward/{month}")
    @Operation(summary = "奖励信息")
    public Result<List<PoopRewardVO>> getReward(@Parameter(description = "月份") @PathVariable String month) {
        List<PoopRewardVO> list = poopSummaryService.findRewardByMonth(YearMonth.parse(month));

        return Result.ok(list);
    }
}
