package com.est.b3.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {
    private String userName;   // 아이디
    private String nickName;   // 닉네임
    private String password;   // 비밀번호 (raw)
}
