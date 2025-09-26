package com.est.b3.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponse {
    private Long id;
    private String userName;
    private String nickName;

    public SignupResponse(Long id, String userName, String nickName) {
        this.id = id;
        this.userName = userName;
        this.nickName = nickName;
    }
}