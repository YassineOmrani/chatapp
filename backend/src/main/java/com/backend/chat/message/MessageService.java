package com.backend.chat.message;

import com.backend.chat.data.Message;
import com.backend.chat.data.MessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageResponse saveAndSendMessage(MessageDTO messageDTO) {
        try {
            if (Objects.isNull(messageDTO.getContent()) && Objects.isNull(messageDTO.getSender()))
                throw new IllegalArgumentException("invalid messsage arguments !");
            this.messageRepository.save(
                Message.builder()
                        .message(messageDTO.getContent())
                        .sender(messageDTO.getSender())
                        .build()
            );
            log.info("Message [{}] saved successfully ", messageDTO);
            return MessageResponse
                    .builder()
                    .content(messageDTO.getContent())
                    .sender(messageDTO.getSender())
                    .build();
        } catch (IllegalArgumentException  illegalArgumentException) {
            log.warn("Invalid message Arguments [{}]", illegalArgumentException.getMessage());
        } catch (DataAccessException dataAccessException) {
            log.warn("Message persistence error [{}]", dataAccessException.getMessage());
        }
        return MessageResponse.builder().build();
    }


    public List<MessageResponse> getAllMessages() {
        try {
            final List<Message> messages = this.messageRepository.findAll();
            return messages
                    .stream()
                    .map(message -> MessageResponse.builder().content(message.getMessage()).sender(message.getSender()).build())
                    .toList();
        } catch (DataAccessException dataAccessException) {
            log.warn("Messages retrieval error {}", dataAccessException.getMessage());
            return List.of();
        }
    }


}
