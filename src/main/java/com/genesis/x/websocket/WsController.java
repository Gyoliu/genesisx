package com.genesis.x.websocket;

import com.alibaba.fastjson.JSON;
import com.genesis.x.vo.MessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * @Author: liuxing
 * @Date: 2019/3/22 16:33
 * @Description:
 */
@Controller
public class WsController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/ws/chat")
    public void handleChat(Principal principal, String msg) {
        MessageVo messageVo = JSON.parseObject(msg, MessageVo.class);
        messageVo.setSelf(false);
        messageVo.setFrom(principal.getName());
        messagingTemplate.convertAndSendToUser(messageVo.getTo(), "/queue/chat", JSON.toJSONString(messageVo));
    }

    @MessageExceptionHandler(Exception.class)
    @SendToUser("/queue/errors")
    public Exception handleExceptions(Exception t){
        t.printStackTrace();
        return t;
    }

}