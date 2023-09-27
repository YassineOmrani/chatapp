package com.backend.chat;

import com.backend.chat.data.MessageDTO;
import com.backend.chat.message.MessageResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public MessageResponse sendMessage(MessageDTO message) {
        // Handle the received message and broadcast it to all connected clients
        return MessageResponse.builder()
                .sender(message.getSender())
                .content(message.getContent())
                .build();
    }
}
