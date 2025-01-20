package com.yuan.loveboot.poop.service.impl;

import com.yuan.loveboot.common.utils.Result;
import com.yuan.loveboot.poop.service.PoopSseService;
import com.yuan.loveboot.poop.vo.PoopUserStateVO;
import com.yuan.loveboot.system.service.SysCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Maverick
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PoopSeeServiceImpl implements PoopSseService {
    // 用于存储所有连接的 SSEEmitter，方便后续推送消息
    private final Map<Integer, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final SysCacheService sysCacheService;

    @Override
    public SseEmitter connect(String token) {
        Integer userId = sysCacheService.getUserId(token);
        if (userId == null) {
            log.info("token无效，连接失败，token：{}", token);
            return null;
        }
        log.debug("用户 {} 已连接", userId);

        // 永不超时
        SseEmitter emitter = new SseEmitter(0L);

        emitters.put(userId, emitter);
        emitter.onCompletion(() -> {
            log.debug("用户 {} 关闭连接", userId);
            emitters.remove(userId);
        });
        emitter.onTimeout(() -> {
            log.debug("用户 {} 连接超时", userId);
            emitters.remove(userId);
        });

        // 发送连接成功消息
        try {
            emitter.send("ping", MediaType.TEXT_PLAIN);
        } catch (Exception e) {
            log.error("推送连接成功消息给用户 {} 失败", userId, e);
            emitters.remove(userId);
        }

        return emitter;
    }

    @Override
    public void sendMessage(int userId, PoopUserStateVO vo) {
        SseEmitter emitter = emitters.get(userId);
        if (emitter != null) {
            try {
                emitter.send(Result.ok(vo), MediaType.APPLICATION_JSON);
                log.debug("推送消息给用户 {}", userId);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                log.error("推送消息给用户 {} 失败", userId);
            }
        } else {
            log.debug("用户 {} 不在线", userId);
        }
    }

    /**
     * 发送心跳包
     * <p>
     * 每 30 秒执行一次
     */
    @Scheduled(fixedRate = 30_000)
    private void sendHeartbeats() {
        emitters.forEach((userId, emitter) -> {
            try {
                // 发送心跳消息
                emitter.send("ping", MediaType.TEXT_PLAIN);

                log.debug("发送心跳消息给用户 {}", userId);
            } catch (Exception e) {
                log.error("发送心跳消息给用户 {} 失败", userId);
                emitters.remove(userId);
            }
        });
    }
}
