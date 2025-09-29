package com.est.b3.controller.api;

import com.est.b3.domain.Boss;
import com.est.b3.domain.ChatRoom;
import com.est.b3.domain.Message;
import com.est.b3.dto.MessageDto;
import com.est.b3.repository.BossRepository;
import com.est.b3.repository.ChatRoomRepository;
import com.est.b3.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatWebSocketController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final BossRepository bossRepository;

    // 메시지 전송
    @MessageMapping("/chat.send")
    public void sendMessage(@Payload MessageDto messageDto) {
        // 엔티티 조회
        ChatRoom chatRoom = chatRoomRepository.findById(messageDto.getChatRoomId())
                .orElseThrow(() -> new IllegalArgumentException("채팅방 없음"));
        Boss sender = bossRepository.findById(messageDto.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("보스 없음"));

        // DB에 메시지 저장
        Message message = Message.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .content(messageDto.getContent())
                .isRead(0)
                .createdAt(LocalDateTime.now())
                .build();
        Message savedMessage = messageRepository.save(message);

        // 브로드캐스트
        simpMessagingTemplate.convertAndSend(
                "/topic/chatroom/" + messageDto.getChatRoomId(),
                savedMessage
        );
    }
}
