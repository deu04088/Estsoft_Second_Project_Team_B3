package com.est.b3.controller.api;

import com.est.b3.domain.Message;
import com.est.b3.dto.ChatRoomDto;
import com.est.b3.dto.MessageDto;
import com.est.b3.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// stomp 채팅 구현 및 채팅 내용 db 저장 컨트롤러
// 이제 채팅방 리스트도 stomp 적용
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    // 채팅방 목록 데이터 반환
    @GetMapping("/list/{bossId}")
    public ResponseEntity<List<ChatRoomDto>> getChatListForUpdate(@PathVariable Long bossId) {
        // 채팅방 리스트를 가져와 정렬
        List<ChatRoomDto> chatRooms = chatService.getChatRoomsByBossId(bossId)
                .stream()
                .map(room -> chatService.toDto(room, bossId))
                .toList();

        // 메시지 시간 기준으로 내림차순 정렬
        List<ChatRoomDto> sortedChatRooms = chatRooms.stream()
                .sorted(Comparator.comparing(ChatRoomDto::getLastMessageCreatedAt,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .collect(Collectors.toList());

        return ResponseEntity.ok(sortedChatRooms);
    }

    // stomp로 채팅
    @MessageMapping("/{roomId}") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    @SendTo("/topic/{roomId}")   //구독하고 있는 장소로 메시지 전송 (목적지)  -> WebSocketConfig Broker에서 적용한건 앞에 붙어줘야됨
    public MessageDto chat(@DestinationVariable Long roomId, MessageDto messageDto) {
        // 채팅 저장
        Message chat = chatService.saveMessage(roomId, messageDto.getSenderId(), messageDto.getContent());
        // 상대방 id
        Long partnerId = chatService.getPartnerIdByChatRoomAndSender(roomId, chat.getSender().getId());

        // 상대방의 구독 채널로 갱신하라는 알림을 보냄
        // 이 채널은 클라이언트의 모든 세션이 구독하고 있어야 함
        if (partnerId != null) {
            messagingTemplate.convertAndSend(
                    "/topic/user/" + partnerId + "/chatlist-update",
                    "{\"roomId\": " + roomId + ", \"lastMessage\": \"" + chat.getContent() + "\"}"
            );
        }

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
