package com.est.b3.service;

import com.est.b3.domain.ChatRoom;
import com.est.b3.domain.Message;
import com.est.b3.repository.ChatRoomRepository;
import com.est.b3.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;

    // 메시지 전송
//    public Message saveMessage(ChatDto dto) {
//        // 챗룸 미아
//        ChatRoom chatRoom = chatRoomRepository.findById(dto.getChatRoomId())
//                .orElseThrow(() -> new IllegalArgumentException("ChatRoom not found"));
//
//        // 보낸 사용자 미아
//        User sender = userRepository.findById(dto.getSenderId())
//                .orElseThrow(() -> new IllegalArgumentException("User not found"));
//
//        Message message = Message.builder()
//                .chatRoom(chatRoom)
//                .sender(sender)
//                .content(dto.getContent())
//                .isRead(0)
//                .createdAt(LocalDateTime.now())
//                .build();
//
//        return messageRepository.save(message);
//    }

    // 내가 속한 채팅방 리스트 조회
    public List<ChatRoom> getChatRoomsByBossId(Long bossId) {
        List<ChatRoom> rooms = new ArrayList<>();
        rooms.addAll(chatRoomRepository.findByUser_Id(bossId)); // 사용자가 user인 경우
        rooms.addAll(chatRoomRepository.findByUser2_Id(bossId)); // 사용자가 user2인 경우
        return rooms;
    }

    // chatroomId로 채팅방 내의 메시지 조회
    public List<Message> getMessagesByChatRoomId(Long chatRoomId) {
        return messageRepository.findByChatRoom_Id(chatRoomId);
    }

    // 메시지 읽기 완료
    public void markAsRead(Long messageId) {
        Message msg = messageRepository.findById(messageId).orElseThrow();
        msg.setIsRead(1);
        messageRepository.save(msg);
    }
}
