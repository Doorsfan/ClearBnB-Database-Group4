package com.company.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
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
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime timestamp;

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public User getWrittenByUser() {
        return writtenByUser;
    }

    public void setWrittenByUser(User writtenByUser) {
        this.writtenByUser = writtenByUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", writtenByUser=" + writtenByUser.getUserId() +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
