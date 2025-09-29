package com.est.b3.dto;

import com.est.b3.domain.ChatRoom;
import lombok.Data;

import java.time.LocalDateTime;

// 챗룸 json 리턴 템플릿
@Data
public class ChatRoomDto {
    private Long id;
    private String userName;
    private String user2Name;
    private LocalDateTime createdAt;

    public ChatRoomDto(ChatRoom room) {
        this.id = room.getId();
        this.userName = room.getUser().getUserName();
        this.user2Name = room.getUser2().getUserName();
        this.createdAt = room.getCreatedAt();
    }
}
