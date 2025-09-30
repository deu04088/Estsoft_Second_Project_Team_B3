package com.est.b3.dto;

import com.est.b3.domain.Boss;
import com.est.b3.domain.ChatRoom;
import com.est.b3.domain.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 챗룸 json 리턴 템플릿
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {
    private Long id;
    private String partnerNickName; // 상대방 닉네임
    private String partnerProfileUrl;
    private LocalDateTime createdAt;
    private String lastMessage;
    private int unreadCount;

    public ChatRoomDto(ChatRoom room, Boss partner, Photo photo) {
        this.id = room.getId();
        this.partnerNickName = partner.getNickName();
        this.createdAt = room.getCreatedAt();
        this.partnerProfileUrl = photo.getS3Url();
    }
}
