package com.est.b3.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String userName;
    private String password;  // raw password (입력값)
}