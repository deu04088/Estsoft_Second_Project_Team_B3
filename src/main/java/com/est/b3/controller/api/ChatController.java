package com.est.b3.controller.api;

import com.est.b3.domain.Message;
import com.est.b3.dto.MessageDto;
import com.est.b3.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    // stomp로 채팅
    @MessageMapping("/{roomId}") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    @SendTo("/topic/{roomId}")   //구독하고 있는 장소로 메시지 전송 (목적지)  -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
    public MessageDto chat(@DestinationVariable Long roomId, MessageDto messageDto) {

        //채팅 저장
        Message chat = chatService.saveMessage(roomId, messageDto.getSenderId(), messageDto.getContent());
        return MessageDto.builder()
                .id(chat.getId())
                .chatRoomId(roomId)
                .senderId(chat.getSender().getId())
                .content(chat.getContent())
                .isRead(chat.getIsRead())
                .createdAt(chat.getCreatedAt())
                .build();
    }

    // 채팅방의 모든 읽지 않은 메시지를 읽음으로 처리
    // isRead 0 -> 1
    @PutMapping("/messages/read/{chatRoomId}")
    public ResponseEntity<Void> markAllMessagesAsRead(
            @PathVariable Long chatRoomId,
            @RequestParam Long bossId) {

        chatService.markAllAsReadByRoomId(chatRoomId, bossId);
        return ResponseEntity.ok().build();
    }

    // 단일 메시지를 읽음 처리
    @PutMapping("/message/read/{messageId}")
    public ResponseEntity<Void> markIndividualMessageAsRead(@PathVariable Long messageId) {
        chatService.markAsRead(messageId);
        return ResponseEntity.ok().build();
    }
}
