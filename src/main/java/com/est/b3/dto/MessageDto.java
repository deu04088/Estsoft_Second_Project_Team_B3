package com.est.b3.dto;

import com.est.b3.domain.Message;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

// 메시지 리턴 템플릿
@Data
public class MessageDto {
    private Long id;
    private Long chatRoomId;
    private Long senderId;
    private String content;
    private Integer isRead;
    private LocalDateTime createdAt;

    public MessageDto(Message message) {
        this.id = message.getId();
        this.chatRoomId = message.getChatRoom().getId();
        this.senderId = message.getSender().getId();
        this.content = message.getContent();
        this.isRead = message.getIsRead();
        this.createdAt = message.getCreatedAt();
    }
}