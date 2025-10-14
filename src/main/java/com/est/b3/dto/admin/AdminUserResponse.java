package com.est.b3.dto.admin;

import com.est.b3.domain.Boss;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserResponse {

    private Long id;
    private String userName;
    private String nickName;
    private String address;
    private String role;
    private Integer state;
    private LocalDateTime createdAt;
    private String userAgent;

    public AdminUserResponse(Boss boss) {
        this.id = boss.getId();
        this.userName = boss.getUserName();
        this.nickName = boss.getNickName();
        this.address = boss.getAddress();
        this.role = boss.getRole();
        this.state = boss.getState();
        this.createdAt = boss.getCreatedAt();
        this.userAgent = boss.getUserAgent();
    }
}
