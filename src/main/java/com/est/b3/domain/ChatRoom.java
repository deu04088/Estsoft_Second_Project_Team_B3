package com.est.b3.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_rooms")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boss_id")
    private Boss user;

    @JoinColumn(name = "boss_id2")
    @ManyToOne(fetch = FetchType.LAZY)
    private Boss user2;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // 채팅방 생성
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.normalizeUsers();
    }

    public static ChatRoom create(Boss bossA, Boss bossB) {
        ChatRoom room = ChatRoom.builder()
                .user(bossA)
                .user2(bossB)
                .build();
        room.normalizeUsers();
        return room;
    }

    // 사용자 id순서에 따라 정렬
    // 이렇게 해야 똑같은 방이 두개가 안생김
    private void normalizeUsers() {
        if (this.user != null && this.user2 != null) {
            // ID가 작은 쪽을 user (boss_id), 큰 쪽을 user2 (boss_id2)에 저장
            if (this.user.getId() > this.user2.getId()) {
                Boss temp = this.user;
                this.user = this.user2;
                this.user2 = temp;
            }
        }
    }
}

