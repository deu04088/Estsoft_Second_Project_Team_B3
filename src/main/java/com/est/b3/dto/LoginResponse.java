package com.est.b3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private Long id;
    private String userName;
    private String nickName;
    private String address; // 동네 인증 여부 확인할 때 사용
}