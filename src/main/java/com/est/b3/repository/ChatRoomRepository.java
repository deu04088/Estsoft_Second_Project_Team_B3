package com.est.b3.repository;

import com.est.b3.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByUser_Id(Long userId);

    List<ChatRoom> findByUser2_Id(Long userId);

    // 두 사용자 ID로 기존 채팅방을 찾음
    // 중복 채팅방을 만들지 않기 위해 생성
    Optional<ChatRoom> findByUser_IdAndUser2_Id(Long userBossId, Long user2BossId);

    // 특정 사용자 ID가 참여한 모든 채팅방을 조회
    // 찾고 내림차순 정렬
    List<ChatRoom> findByUser_IdOrUser2_IdOrderByCreatedAtDesc(Long bossId1, Long bossId2);
}

