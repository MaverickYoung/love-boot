package com.yuan.loveboot.poop.controller;

import com.yuan.loveboot.poop.po.PoopSummary;
import com.yuan.loveboot.poop.service.PoopLogService;
import com.yuan.loveboot.utils.Result;
import com.yuan.loveboot.utils.YearMonthRange;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("info")
    @Operation(summary = "按月统计用户便便数量")
    public Result<List<PoopSummary>> countUserPoopMonthly(@RequestBody YearMonthRange range) {
        List<PoopSummary> list = poopLogService.countUserPoopMonthly(range);

        return Result.ok(list);
    }
}
