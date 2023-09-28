package com.backend.chat.message;

import com.backend.chat.data.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository  extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m ORDER BY m.date DESC")
    List<Message> findAllByOrderByDateDesc();
}
