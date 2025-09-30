package com.est.b3.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 세션에 저장할 최소 사용자 정보 DTO
 */
@Getter
@AllArgsConstructor
public class SessionUserDTO {
    private Long id;
    private String userName;
    private String nickName;
}
