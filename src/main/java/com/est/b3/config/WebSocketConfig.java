package com.est.b3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

// https://velog.io/@kmh916/Spring-Boot-%EC%B1%84%ED%8C%85-%EA%B5%AC%ED%98%84SockJSStomp-1.-%EA%B8%B0%EB%B3%B8-%EB%8F%99%EC%9E%91-%EA%B5%AC%ED%98%84
// 인텔리제이가 websocket 설치하라고 나올텐데 설치할 것!
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    // 메시지 브로커를 활성화하고 subscribe 메시지 접두사 설정
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 구독 경로
        config.enableSimpleBroker("/topic");
        // 메시지 전송 prefix
        config.setApplicationDestinationPrefixes("/app");
    }

    // 웹소켓 엔트포인트를 지정
    // 추후 클라에서 해당 경로로 서버와 핸드셰이크하게 됨
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // SockJS 엔드포인트
        registry.addEndpoint("/ws-chat")
                .setAllowedOriginPatterns("*") // 개발 시 모든 origin 허용
                .withSockJS();
    }
}