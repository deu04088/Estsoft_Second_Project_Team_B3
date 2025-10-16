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

// 채팅 서비스 로직
// 1. 채팅방 리스트 조회
// 2. 채팅방 내 모든 메시지 읽기 처리
// 3. 특정 메시지 읽기 처리
// 4. stomp 메시지 저장
// 5. 채팅 상대 정보 겟
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

        // 여러 식당을 운영하는 사장의 경우 가장 낮은 id의 사진을 가져옴
        // 대표 식당의 이미지 느낌
        List<Restaurant> restaurants = restaurantRepository.findAllByBossIdOrderByIdAsc(partner.getId());
        if (!restaurants.isEmpty()) {
            Restaurant firstRestaurant = restaurants.get(0); // 가장 낮은 ID
            Photo photo = firstRestaurant.getPhoto();
            if (photo != null && photo.getS3Url() != null) {
                profileUrl = photo.getS3Url();
            }
        }

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

        // todto에 넣어서 반환, 상대방 정보만 채움
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

        // 저장 및 DB ID, createdAt이 채워진 엔티티 리턴
        return messageRepository.save(message);
    }

    // 채팅방의 메시지를 받는 사람의 id를 찾음
    public Long getPartnerIdByChatRoomAndSender(Long chatRoomId, Long senderId) {
        ChatRoom room = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new IllegalArgumentException("챗룸 찾지 못함: " + chatRoomId));

        // 채팅방의 두 사용자를 비교해 메시지를 받는 사용자를 찾음
        Long user1Id = room.getUser().getId();
        Long user2Id = room.getUser2().getId();

        if (user1Id.equals(senderId)) {
            return user2Id;
        } else if (user2Id.equals(senderId)) {
            return user1Id;
        } else {
            throw new IllegalStateException("Sender ID " + senderId + " chatRoom ID " + chatRoomId);
        }
    }

    // 채팅방 만들기(상점소개 게시물에서 사용)
    @Transactional
    public Long getOrCreateChatRoom(Long myBossId, Long partnerBossId) {
        // 자기와의 채팅 기능 없음
        if (myBossId.equals(partnerBossId)) {
            throw new IllegalArgumentException("자기 자신과 채팅방을 생성할 수 없습니다.");
        }

        // 낮은 숫자의 id가 1, 큰 숫자의 id가 2
        Long boss1Id = Math.min(myBossId, partnerBossId);
        Long boss2Id = Math.max(myBossId, partnerBossId);

        // 기존 채팅방 존재 확인
        Optional<ChatRoom> existingRoom = chatRoomRepository.findByUser_IdAndUser2_Id(boss1Id, boss2Id);

        if (existingRoom.isPresent()) {
            // 이미 존재하는 경우, 해당 ID를 반환
            return existingRoom.get().getId();
        }

        // 채팅방이 없다면 새로 생성
        Boss boss1 = bossRepository.findById(boss1Id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 ID를 찾을 수 없습니다: " + boss1Id));
        Boss boss2 = bossRepository.findById(boss2Id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 ID를 찾을 수 없습니다: " + boss2Id));

        // ChatRoom의 정적 팩토리 메서드를 통해 생성 (내부에서 ID 정규화 처리)
        ChatRoom newRoom = ChatRoom.create(boss1, boss2);
        ChatRoom savedRoom = chatRoomRepository.save(newRoom);

        // 채팅방 생성되면 자동으로 메시지 하나 보냄
        Message initialMessage = Message.builder()
                .chatRoom(savedRoom)
                .sender(bossRepository.getReferenceById(myBossId)) // 메시지 보낸 사람은 myBossId
                .content("안녕하세요~ 항상 수고하십니다~")
                .isRead(0) // 초기 메시지는 상대방이 읽지 않은 상태
                .createdAt(LocalDateTime.now())
                .build();

        messageRepository.save(initialMessage);

        return newRoom.getId();
    }
}
