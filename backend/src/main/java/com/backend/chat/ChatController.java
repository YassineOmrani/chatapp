package com.backend.chat;

import com.backend.chat.data.MessageDTO;
import com.backend.chat.message.MessageResponse;
import com.backend.chat.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MessageService messageService;

    @MessageMapping("/send")
    @SendTo("/topic/messages")
    public MessageResponse sendMessage(MessageDTO messageDTO) {
        // Handle the received message and broadcast it to all connected clients
        return messageService.saveAndSendMessage(messageDTO);
    }

    @MessageMapping("/all")
    @SendTo("/topic/all-messages")
    public List<MessageResponse> getAllMessages() {
        // Retrieve all messages and send them to the connected client
        return messageService.getAllMessages();
    }


}
