package com.est.b3.service;

import com.est.b3.domain.*;
import com.est.b3.dto.ChatRoomDto;
import com.est.b3.dto.MessageDto;
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

    // 메시지 읽기 완료
    public void markAsRead(Long messageId) {
        Message msg = messageRepository.findById(messageId).orElseThrow();
        // 1로 변경
        msg.markAsRead();
        messageRepository.save(msg);
    }

    // Dto로 변환
    public ChatRoomDto toDto(ChatRoom room, Long bossId) {
        // 상대 사용자(partner) 찾기
        Boss partner;
        // 사용자 1/2 중 검색
        if (room.getUser().getId().equals(bossId)) {
            partner = room.getUser2();
        } else if (room.getUser2().getId().equals(bossId)) {
            partner = room.getUser();
        } else {
            throw new IllegalStateException("찾는 사용자 없음.");
        }

        // 상대 사용자 Boss ID로 Restaurant에서 사진 찾기
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
        // 마지막 메시지 내용
        Optional<Message> lastMessageOpt =
                messageRepository.findTop1ByChatRoom_IdOrderByCreatedAtDesc(room.getId());
        String lastMessageContent = lastMessageOpt
                .map(Message::getContent)
                .orElse("대화 내용 없음"); // 메시지가 없으면 기본 문구

        // 읽지 않은 메시지 수
        int unreadCount = messageRepository.countUnreadMessagesByChatRoomAndCurrentBoss(
                room.getId(),
                bossId // 현재 로그인한 사용자(Boss) ID를 전달
        );

        // dto로 변환
        return ChatRoomDto.builder()
                .id(room.getId())
                .partnerNickName(partner.getNickName())
                .partnerProfileUrl(profileUrl)
                .createdAt(room.getCreatedAt())
                .lastMessage(lastMessageContent)
                .unreadCount(unreadCount)
                .build();
    }

    // chatroomid로 메시지 리스트 반환
    public List<MessageDto> getMessagesByChatRoomId(Long chatRoomId) {
        // dto에 넣어서 반환
        List<Message> messages = messageRepository.findByChatRoomIdOrderByCreatedAtAsc(chatRoomId);

        return messages.stream()
                .map(MessageDto::new)
                .toList();
    }

    // 채팅방의 상대 사용자 정보와 채팅방 정보를 가져옴
    public ChatRoomDto getChatRoomDetail(Long chatRoomId, Long bossId) {
        // ChatRoom으로
        ChatRoom room = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("Chat room not found: " + chatRoomId));

        //todto에 넣어서 반환, 상대방 정보만 채움
        return toDto(room, bossId);
    }
}
