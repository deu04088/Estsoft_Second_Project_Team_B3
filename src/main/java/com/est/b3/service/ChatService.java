package com.est.b3.service;

import com.est.b3.domain.*;
import com.est.b3.dto.ChatRoomDto;
import com.est.b3.dto.MessageDto;
import com.est.b3.repository.BossRepository;
import com.est.b3.repository.ChatRoomRepository;
import com.est.b3.repository.MessageRepository;
import com.est.b3.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final MessageRepository messageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final RestaurantRepository restaurantRepository;
    private final BossRepository bossRepository;

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

    // 채팅방 내의 모든 메시지 읽음으로 처리
    @Transactional
    public void markAllAsReadByRoomId(Long chatRoomId, Long currentBossId) {
        // 상대방의 메시지만 찾음
        List<Message> unreadMessages = messageRepository
                .findByChatRoom_IdAndIsReadAndSender_IdNot(chatRoomId, 0, currentBossId);

        for (Message message : unreadMessages) {
            message.markAsRead();
        }
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

        // 마지막 메시지 생성 시간
        LocalDateTime lastMessageTime = lastMessageOpt
                .map(Message::getCreatedAt)
                .orElse(room.getCreatedAt());

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
                .lastMessageCreatedAt(lastMessageTime)
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

    // STOMP 메시지 저장
    @Transactional
    public Message saveMessage(Long chatRoomId, Long senderId, String content) {

        ChatRoom chatRoom = chatRoomRepository.getReferenceById(chatRoomId);
        Boss sender = bossRepository.getReferenceById(senderId);

        Message message = Message.builder()
                .chatRoom(chatRoom)
                .sender(sender)
                .content(content)
                .createdAt(LocalDateTime.now())
                .build();

        // 3. 저장 및 DB ID, createdAt이 채워진 엔티티 리턴
        return messageRepository.save(message);
    }
}
