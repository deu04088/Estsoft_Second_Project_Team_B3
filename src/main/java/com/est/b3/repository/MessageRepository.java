package com.est.b3.repository;

import com.est.b3.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByChatRoom_Id(Long chatRoomId);
    List<Message> findBySender_Id(Long senderId);
}
