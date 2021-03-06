package com.company.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@Table(name = "Message")
public class Message {
    @Id
    @GenericGenerator(name="temp", strategy = "increment")
    @GeneratedValue(generator="temp")
    @Column(name = "messageId")
    private Integer messageId;
    @ManyToOne
    @JoinColumn(name="writtenById")
    private User writtenByUser;
    @ManyToOne
    @JoinColumn(name="recipientId")
    private User recipientUser;
    private String content;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
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

    public User getRecipientUser() {
        return recipientUser;
    }

    public void setRecipientUser(User recipientUser) {
        this.recipientUser = recipientUser;
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
                ", recipientUser=" + recipientUser.getUserId() +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
