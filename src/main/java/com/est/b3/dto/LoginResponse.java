package com.est.b3.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private Long id;
    private String userName;
    private String nickName;
    private String address; // 동네 인증 여부 확인할 때 사용

    public LoginResponse(Long id, String userName, String nickName, String address) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
        this.address = address;
    }
}