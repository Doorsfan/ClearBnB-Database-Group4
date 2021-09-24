package com.company.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Message")
public class Message {
    @Id
    @Column(name = "message_id")
    private Integer messageId;
    @ManyToOne
    @JoinColumn(name="written_by_id")
    private User writtenByUser;
    private String content;
    private LocalDateTime timestamp;
}
