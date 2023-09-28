package com.backend.chat.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "messages")
public class Message {

    @Id()
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @Column(name = "message")
    @NonNull
    String message;
    @Column(name = "sender")
    @NonNull
    String sender;

    @Column(name = "date")
    LocalDateTime date;

}
