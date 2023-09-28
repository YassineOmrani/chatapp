package com.backend.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enable a simple in-memory message broker to handle message routing
        config.enableSimpleBroker("/topic");
        // Set the prefix for the application destination (where the client sends messages)
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // Register the WebSocket endpoints
        registry.addEndpoint("/send").setAllowedOrigins("http://localhost:8008").withSockJS();
        registry.addEndpoint("/all").setAllowedOrigins("http://localhost:8008").withSockJS();
    }
}
