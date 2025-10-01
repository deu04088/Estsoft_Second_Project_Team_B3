package com.est.b3.repository;

import com.est.b3.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatRoom_Id(Long chatRoomId);

    List<Message> findBySender_Id(Long senderId);

    // 메시지 오름차순 정렬해서 가져옴 chatroomid로 찾아서
    List<Message> findByChatRoomIdOrderByCreatedAtAsc(Long chatRoomId);

    // 해당 방의 마지막 메시지 조회
    Optional<Message> findTop1ByChatRoom_IdOrderByCreatedAtDesc(Long chatRoomId);

    // 안 읽은 메시지 수 조회
    // 해당 방 id로 현재 사용자 id로 isRead가 0인 메시지 개수 카운트
    // JPQL로 구현
    @Query("SELECT COUNT(m) FROM Message m " +
            "WHERE m.chatRoom.id = :chatRoomId " +
            "AND m.sender.id != :currentBossId " +
            "AND m.isRead = 0")
    int countUnreadMessagesByChatRoomAndCurrentBoss(
            @Param("chatRoomId") Long chatRoomId,
            @Param("currentBossId") Long currentBossId
    );
}
