package com.yuan.loveboot.system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 首页 欢迎信息
 *
 * @author Maverick
 */
@Tag(name = "首页")
@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "项目启动成功";
    }
}
