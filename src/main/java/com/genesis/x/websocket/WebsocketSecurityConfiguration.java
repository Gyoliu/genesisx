package com.genesis.x.websocket;

import com.genesis.x.utils.WebUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.lang.Nullable;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @Author: liuxing
 * @Date: 2019/3/19 17:01
 * @Description:
 */
@Slf4j
@Configuration
@EnableWebSocketMessageBroker
public class WebsocketSecurityConfiguration extends AbstractSecurityWebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket").setAllowedOrigins("*").addInterceptors(new HandshakeInterceptor(){
            @Override
            public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
                if(serverHttpRequest instanceof ServletServerHttpRequest) {
                    ServletServerHttpRequest servletRequest = (ServletServerHttpRequest)serverHttpRequest;
                    HttpSession session = servletRequest.getServletRequest().getSession(false);
                    if(session != null) {
                        log.info("-------------beforeHandshake sessionId:{}", session.getId());
                        map.put("SPRING.SESSION.ID", session.getId());
                    }
                }
                return true;
            }

            @Override
            public void afterHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, @Nullable Exception e){
            }
        });
    }

    @Override
    protected void customizeClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(new SecurityContextChannelInterceptor("Authorization"));
        registration.interceptors(new ChannelInterceptor(){
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String jwtToken = accessor.getFirstNativeHeader("Authorization");
                    log.info("webSocket token is {}", jwtToken);
                    if (StringUtils.isNotEmpty(jwtToken)) {
                        jwtToken = jwtToken.replace("bearer ", "");
                        Map sessionAttributes = SimpMessageHeaderAccessor.getSessionAttributes(message.getHeaders());
                        sessionAttributes.put("Authorization", new DefaultCsrfToken("Authorization", "Authorization", jwtToken));
                        Authentication sessionUserByToken = WebUtils.getSessionUserByToken(jwtToken);
                        SecurityContextHolder.getContext().setAuthentication(sessionUserByToken);
                        accessor.setUser(sessionUserByToken);
                    } else {
                        throw new RuntimeException(HttpStatus.UNAUTHORIZED.toString());
                    }
                } else if(StompCommand.DISCONNECT.equals(accessor.getCommand())){
                    log.error("StompCommand.DISCONNECT, message:{}", accessor.getMessage());
                } else if(StompCommand.ERROR.equals(accessor.getCommand())){
                    String name = accessor.getUser().getName();
                    log.error("StompCommand.ERROR:{}, message:{}", name, accessor.getMessage());
                }
                return message;
            }
        });
    }

    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
        /*messages.nullDestMatcher().authenticated()
                .simpSubscribeDestMatchers("/socket/errors").permitAll()
                .simpDestMatchers("/socket*//**").authenticated()
                .simpSubscribeDestMatchers("/user*//**", "/topic*//**").authenticated()
                //.simpTypeMatchers(MESSAGE, SUBSCRIBE).denyAll()
                .anyMessage().denyAll();*/
       messages.anyMessage().permitAll().nullDestMatcher().permitAll();
    }

    @Override
    protected boolean sameOriginDisabled() {
        // 禁用csrfChannelInterceptor  源码查看AbstractSecurityWebSocketMessageBrokerConfigurer.configureClientInboundChannel方法
        return true;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 应用请求前缀
        registry.setApplicationDestinationPrefixes("/app");
        // 推送消息前缀
        registry.enableSimpleBroker("/topic", "/queue");
        //推送用户前缀
        registry.setUserDestinationPrefix("/user");
    }
}