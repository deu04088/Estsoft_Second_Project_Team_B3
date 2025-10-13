package com.est.b3.dto.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {
    private int status;      // HTTP 상태 코드
    private String message;  // 성공 메시지
    private T data;          // 실제 데이터

    // 성공 응답
    public static <T> ApiResponse<T> success(int status, String message, T data) {
        return new ApiResponse<>(status, message, data);
    }
}