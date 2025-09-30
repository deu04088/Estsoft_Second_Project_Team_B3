package com.est.b3.service;

import com.est.b3.domain.*;
import com.est.b3.dto.ChatRoomDto;
import com.est.b3.repository.ChatRoomRepository;
import com.est.b3.repository.MessageRepository;
import com.est.b3.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final RestaurantRepository restaurantRepository;

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
        // 1로 변경
        msg.markAsRead();
        messageRepository.save(msg);
    }

    public ChatRoomDto toDto(ChatRoom room, Long bossId) {
        // 상대 사용자(partner) 찾기
        Boss partner;
        // 사용자 1/2 중 검색
        if (room.getUser().getId().equals(bossId)) {
            partner = room.getUser2();
        } else if (room.getUser2().getId().equals(bossId)) {
            partner = room.getUser();
        } else {
            throw new IllegalStateException("Boss is not a member of this chat room.");
        }

        // 상대 사용자 Boss ID로 Restaurant 찾기
        String profileUrl = "/images/default-profile.png"; // 기본값 - 없으면 들어감
        Optional<Restaurant> restaurantOpt = restaurantRepository.findByBossId(partner.getId());

        if (restaurantOpt.isPresent()) {
            Restaurant restaurant = restaurantOpt.get();
            Photo photo = restaurant.getPhoto();

            if (photo != null) {
                profileUrl = photo.getS3Url();
            }
        }

        // DTO로 반환
        return ChatRoomDto.builder()
                .id(room.getId())
                .partnerNickName(partner.getNickName())
                .partnerProfileUrl(profileUrl)
                .createdAt(room.getCreatedAt())
                .build();
    }
}
