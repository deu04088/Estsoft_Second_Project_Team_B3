package com.est.b3.controller.api;

import com.est.b3.domain.ChatRoom;
import com.est.b3.domain.Message;
import com.est.b3.dto.ChatRoomDto;
import com.est.b3.dto.MessageDto;
import com.est.b3.repository.BossRepository;
import com.est.b3.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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
    @GetMapping("/messages/{chatRoomId}")
    public List<MessageDto> getMessageByChatRoomId(@PathVariable Long chatRoomId) {
        List<Message> messages = chatService.getMessagesByChatRoomId(chatRoomId);
        return messages.stream().map(MessageDto::new).toList();
    }
}
