package com.est.b3.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 채팅방 json 리턴 템플릿
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto {
    private Long id;
    private String partnerNickName; // 상대방 닉네임
    private String partnerProfileUrl;
    private LocalDateTime createdAt;
    private String lastMessage; // 마지막 메시지, 전송 시간
    private LocalDateTime lastMessageCreatedAt;
    private int unreadCount; // 안읽은 메시지 수
}
