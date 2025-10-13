package com.est.b3.controller.api;

import com.est.b3.dto.*;
import com.est.b3.dto.api.ApiResponse;
import com.est.b3.exception.CustomException;
import com.est.b3.exception.ErrorCode;
import com.est.b3.service.BossService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bosses") // prefix 붙여주는 게 RESTful
@RequiredArgsConstructor
public class BossController {

    private final BossService bossService;

    /**
     * 회원가입
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<SignupResponse>> signup(@RequestBody SignupRequest request) {
        SignupResponse response = bossService.signup(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success(201, "회원가입 성공", response));
    }

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(
            @RequestBody LoginRequest request,
            HttpSession session
    ) {
        LoginResponse response = bossService.login(request, session);

        return ResponseEntity
                .ok(ApiResponse.success(200, "로그인 성공", response));
    }

    /**
     * 로그아웃
     */
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout(HttpSession session) {

        bossService.logout(session);

        return ResponseEntity
                .ok(ApiResponse.success(200, "로그아웃 완료", null));
    }

    /**
     * 세션 값 확인 (디버깅용)
     */
    @GetMapping("/session-check")
    public ResponseEntity<ApiResponse<SessionUserDTO>> sessionCheck(HttpSession session) {
        SessionUserDTO loginBoss = (SessionUserDTO) session.getAttribute("loginBoss");

        if (loginBoss == null) {
            throw new CustomException(ErrorCode.SESSION_NOT_FOUND);
        }

        return ResponseEntity
                .ok(ApiResponse.success(200, "세션에 로그인 정보가 있습니다.", loginBoss));
    }
}
