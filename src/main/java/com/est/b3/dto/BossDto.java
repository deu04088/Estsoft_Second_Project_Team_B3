package com.est.b3.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BossDto {
    private Long id;
    private String userName;
    private String nickName;
    private String password;
    private String address;
    private String siDo;
    private String guGun;
    private String dongEupMyeon;
    private LocalDateTime createTime;
    private String role;
    private Integer state;

    public BossDto(Long id, String userName, String nickName, String password,
                   String address, String siDo, String guGun, String dongEupMyeon,
                   LocalDateTime createTime, String role, Integer state) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
        this.address = address;
        this.siDo = siDo;
        this.guGun =  guGun;
        this.dongEupMyeon = dongEupMyeon;
        this.createTime = createTime;
        this.role = role;
        this.state = state;
    }
}

