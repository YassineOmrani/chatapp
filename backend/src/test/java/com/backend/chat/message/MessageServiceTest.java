package com.backend.chat.message;
import com.backend.chat.data.Message;
import com.backend.chat.data.MessageDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {
    @Mock
    MessageRepository messageRepository;
    @InjectMocks
    MessageService messageService;
    @Test
    @DisplayName("GetMessage Valid Message DTO is Ok")
    void getMessage_validMessageDTO_isOk() {
        final MessageResponse expectedResponse = MessageResponse
                .builder()
                .sender("yassine")
                .content("message")
                .build();
        final MessageDTO messageDTO = MessageDTO.builder()
                .sender("yassine")
                .content("message")
                .build();
        final Message entity = Message.builder().message(messageDTO.getContent()).sender(messageDTO.getSender()).build();
        when(this.messageRepository.save(any(Message.class))).thenReturn(entity);
        MessageResponse actualMessageResponse = this.messageService.getMessage(messageDTO);
        assertEquals(expectedResponse, actualMessageResponse);
    }

    @Test
    @DisplayName("GetMessage invalid Message DTO is Ok")
    void getMessage_invalidMessageDTO_isOk() {
        final MessageResponse expectedResponse = MessageResponse
                .builder()
                .build();

        final MessageDTO messageDTO = MessageDTO.builder()
                .build();

        MessageResponse actualMessageResponse = this.messageService.getMessage(messageDTO);
        assertEquals(expectedResponse, actualMessageResponse);
    }


    @Test()
    @DisplayName("GetMessage when exception thrown returns empty MessageResponse (null Arguments)")
    void getMessage_exceptionThrown_isOk() {
        final MessageResponse expectedMessageResponse = MessageResponse.builder().build();

        final MessageDTO messageDTO = MessageDTO
                .builder()
                .build();

        final MessageResponse actualMessageResponse = this.messageService.getMessage(messageDTO);
        assertEquals(expectedMessageResponse, actualMessageResponse);
    }


    @Test()
    @DisplayName("GetAllMessages isOkay")
    void getAllMessages_isOk() {
        final List<MessageResponse> expectedResponse = List.of(
                MessageResponse.builder()
                        .content("content")
                        .sender("user")
                        .build()
        );

        when(this.messageRepository.findAll()).thenReturn(List.of(
                Message.builder()
                        .message("content")
                        .sender("user")
                        .build()
        ));

        final List<MessageResponse> actualResponse = this.messageService.getAllMessages();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test()
    @DisplayName("GetAllMessages returns empty list on failure")
    void getAllMessages_ExceptionThrown_isOk() {
        final List<MessageResponse> expectedResponse = List.of();

        when(this.messageRepository.findAll()).thenThrow(new EmptyResultDataAccessException(0));

        final List<MessageResponse> actualResponse = this.messageService.getAllMessages();
        assertEquals(expectedResponse, actualResponse);
    }



}