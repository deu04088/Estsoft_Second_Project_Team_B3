package com.est.b3.service;

import com.est.b3.domain.ChatRoom;
import com.est.b3.domain.Message;
import com.est.b3.domain.User;
import com.est.b3.dto.ChatDto;
import com.est.b3.repository.ChatRoomRepository;
import com.est.b3.repository.MessageRepository;
import com.est.b3.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    // 메시지 전송
    public Message saveMessage(ChatDto dto) {
        // 챗룸 미아
        ChatRoom chatRoom = chatRoomRepository.findById(dto.getChatRoomId())
                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found"));

        // 보낸 사용자 미아
        User sender = userRepository.findById(dto.getSenderId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Message message = Message.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .content(dto.getContent())
                .isRead(0)
                .createdAt(LocalDateTime.now())
                .build();

        return messageRepository.save(message);
    }

    // 메시지 조회
    public List<Message> getMessages(Long chatRoomId) {
        return messageRepository.findByChatRoom_Id(chatRoomId);
    }

//     메시지 읽기 완료
    public void markAsRead(Long messageId) {
        Message msg = messageRepository.findById(messageId).orElseThrow();
        msg.setIsRead(1);
        messageRepository.save(msg);
    }
}
