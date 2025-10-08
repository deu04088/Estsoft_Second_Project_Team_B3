package com.est.b3.controller.api;

import com.est.b3.domain.ChatRoom;
import com.est.b3.domain.Message;
import com.est.b3.dto.ChatRoomDto;
import com.est.b3.dto.MessageDto;
import com.est.b3.repository.BossRepository;
import com.est.b3.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;
    private final BossRepository bossRepository;

    // 사용자 id로 채팅방 리스트 조회
//    @GetMapping("/rooms/{bossId}")
//    public List<ChatRoomDto> getChatRoomsByUserId(@PathVariable Long bossId) {
//        List<ChatRoom> rooms = chatService.getChatRoomsByBossId(bossId);
//        return rooms.stream()
//                .map(ChatRoomDto::new)
//                .toList();
//    }

    // 채팅방 id로 메시지 리스트 조회
//    @GetMapping("/messages/{chatRoomId}")
//    public List<MessageDto> getMessageByChatRoomId(@PathVariable Long chatRoomId) {
//        List<Message> messages = chatService.getMessagesByChatRoomId(chatRoomId);
//        return messages.stream().map(MessageDto::new).toList();
//    }

    // stomp로 채팅
    @MessageMapping("/{roomId}") //여기로 전송되면 메서드 호출 -> WebSocketConfig prefixes 에서 적용한건 앞에 생략
    @SendTo("/topic/{roomId}")   //구독하고 있는 장소로 메시지 전송 (목적지)  -> WebSocketConfig Broker 에서 적용한건 앞에 붙어줘야됨
    public MessageDto chat(@DestinationVariable Long roomId, MessageDto messageDto) {

        //채팅 저장
        Message chat = chatService.saveMessage(roomId, messageDto.getSenderId(), messageDto.getContent());
        return MessageDto.builder()
                .chatRoomId(roomId)
                .senderId(chat.getSender().getId())
                .content(chat.getContent())
                .build();
    }
}
