package com.est.b3.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

@Getter
@Entity
@Table(name = "bosses")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Boss {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private Long id;

    @Column(name = "user_name", length = 20, nullable = false, unique = true)
    private String userName;   // 로그인 ID

    @Column(name = "nick_name", length = 30, nullable = false, unique = true)
    private String nickName;   // 닉네임

    @Column(name = "password", length = 60, nullable = false)
    private String password;   // 암호화된 비밀번호

    @Column(name = "address", length = 50)
    private String address;    // 동네 인증 주소

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "role", length = 10, nullable = false)
    private String role;   // ROLE_USER, ROLE_ADMIN

    @Column(name = "state", nullable = false)
    private Integer state; // 1: 활성, 0: 탈퇴 등

    @OneToMany(mappedBy = "boss", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Like> likes = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (this.role == null) this.role = "ROLE_USER";
        if (this.state == null) this.state = 1;
    }
}
