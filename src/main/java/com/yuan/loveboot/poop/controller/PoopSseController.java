package com.yuan.loveboot.poop.controller;

import com.yuan.loveboot.poop.service.PoopSseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author Maverick
 */
@RestController
@RequestMapping("/poop/sse")
@RequiredArgsConstructor
@Slf4j
public class PoopSseController {

    private final PoopSseService poopSseService;

    // 用户连接 SSE 的接口
    @GetMapping("/connect/{token}")
    public SseEmitter connect(@PathVariable String token) {
        return poopSseService.connect(token);
    }
}
