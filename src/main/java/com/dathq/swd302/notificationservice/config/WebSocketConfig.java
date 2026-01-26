package com.dathq.swd302.notificationservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author matve
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Kích hoạt một "broker" đơn giản để đẩy tin nhắn về client
        // Client sẽ subscribe vào các đường dẫn bắt đầu bằng /topic
        config.enableSimpleBroker("/topic");
        // Các tin nhắn từ client gửi lên server sẽ bắt đầu bằng /app
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Điểm kết nối để client dùng (ví dụ: dùng thư viện SockJS)
        registry.addEndpoint("/ws-notification").setAllowedOriginPatterns("*").withSockJS();
    }
}
