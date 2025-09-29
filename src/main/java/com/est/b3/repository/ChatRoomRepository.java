package com.est.b3.repository;

import com.est.b3.domain.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    List<ChatRoom> findByUser_Id(Long userId);
    List<ChatRoom> findByUser2_Id(Long userId);
}

