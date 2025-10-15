package com.est.b3.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    private String userName;   // 로그인 ID

    private String nickName;   // 닉네임

    private String password;   // 암호화된 비밀번호

    private String address;    // 동네 인증 주소 전체주소

    private String siDo;    // 동네 인증 주소 시도

    private String guGun;    // 동네 인증 주소 구군

    private String dongEupMyeon;    // 동네 인증 주소 동읍면

    private LocalDateTime createdAt;

    private String role;   // ROLE_USER, ROLE_ADMIN

    private Integer state; // 1: 활성, 0: 탈퇴 등

    private String userAgent;  // 가입 당시 브라우저/기기 정보

    @OneToMany(mappedBy = "boss", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Like> likes = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();

        if (this.role == null) this.role = "ROLE_USER";
        if (this.state == null) this.state = 1;
    }

    // 도메인 메서드: 주소 업데이트
    public void updateAddress(String fullAddress, String siDo, String guGun, String dongEupMyeon) {
      this.address = fullAddress;
      this.siDo = siDo;
      this.guGun = guGun;
      this.dongEupMyeon = dongEupMyeon;
    }

    // [도메인 메서드] 주소 변경
    public void toggleState() {
        this.state = (this.state == 1) ? 0 : 1;
    }

}
