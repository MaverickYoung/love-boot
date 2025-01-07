package com.yuan.loveboot.poop.service;

import com.yuan.loveboot.poop.vo.PoopUserStateVO;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author Maverick
 */
public interface PoopSseService {
    /**
     * 连接服务端
     *
     * @param token 访问令牌
     */
    SseEmitter connect(String token);

    /**
     * 推送消息
     *
     * @param userId 用户id
     * @param vo     消息内容
     */
    void sendMessage(int userId, PoopUserStateVO vo);

    /**
     * 发送心跳包
     */
    void sendHeartbeats();
}
