package com.est.b3.controller.api;

import com.est.b3.domain.Boss;
import com.est.b3.dto.SessionUserDTO;
import com.est.b3.dto.admin.VerifyRequest;
import com.est.b3.dto.api.ApiResponse;
import com.est.b3.exception.CustomException;
import com.est.b3.exception.ErrorCode;
import com.est.b3.repository.BossRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final BossRepository bossRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 관리자 비밀번호 검증 (모달에서 호출)
     */
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<Void>> verifyAdmin(@RequestBody VerifyRequest request, HttpSession session) {

        SessionUserDTO sessionUser = (SessionUserDTO) session.getAttribute("loginBoss");


        if (sessionUser == null) {
            throw new CustomException(ErrorCode.SESSION_NOT_FOUND);
        }


        Boss boss = bossRepository.findByUserName(sessionUser.getUserName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));


        // 관리자 권한 확인
        if (!"ROLE_ADMIN".equals(boss.getRole())) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }


        // 비밀번호 확인
        if (!passwordEncoder.matches(request.getPassword(), boss.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH);
        }


        // 세션에 관리자 인증 플래그 저장
        session.setAttribute("adminAuth", true);
        session.setAttribute("adminAuthExpiry", LocalDateTime.now().plusMinutes(10));

        return ResponseEntity.ok(ApiResponse.success(200, "관리자 인증 성공", null));
    }

    /**
     * 세션 상태 확인 (AJAX 폴링용)
     */
    @GetMapping("/check")
    public ResponseEntity<ApiResponse<Void>> checkAdminSession(HttpSession session) {

        Boolean isAdminAuth = (Boolean) session.getAttribute("adminAuth");
        LocalDateTime expiry = (LocalDateTime) session.getAttribute("adminAuthExpiry");

        if (isAdminAuth == null || !isAdminAuth || expiry == null || expiry.isBefore(LocalDateTime.now())) {
            session.removeAttribute("adminAuth");
            session.removeAttribute("adminAuthExpiry");

            throw new CustomException(ErrorCode.ADMIN_SESSION_EXPIRED);
        }


        return ResponseEntity.ok(ApiResponse.success(200, "세션이 유효합니다.", null));
    }

    /**
     * 세션 남은 시간 확인
     */
    @GetMapping("/remaining-time")
    public ResponseEntity<ApiResponse<Long>> getRemainingTime(HttpSession session) {

        Boolean isAdminAuth = (Boolean) session.getAttribute("adminAuth");
        LocalDateTime expiry = (LocalDateTime) session.getAttribute("adminAuthExpiry");

        if (isAdminAuth == null || !isAdminAuth || expiry == null) {
            throw new CustomException(ErrorCode.ADMIN_SESSION_EXPIRED);
        }

        long remainingSeconds = java.time.Duration.between(LocalDateTime.now(), expiry).getSeconds();

        if (remainingSeconds <= 0) {
            session.removeAttribute("adminAuth");
            session.removeAttribute("adminAuthExpiry");

            throw new CustomException(ErrorCode.ADMIN_SESSION_EXPIRED);
        }

        return ResponseEntity.ok(ApiResponse.success(200, "남은 시간", remainingSeconds));
    }


    /**
     * 세션 남은 시간 연장
     */
    @PostMapping("/verify/extend")
    public ResponseEntity<ApiResponse<Void>> extendAdminSession(HttpSession session) {

        Boolean isAdminAuth = (Boolean) session.getAttribute("adminAuth");

        if (isAdminAuth == null || !isAdminAuth) {

            throw new CustomException(ErrorCode.ADMIN_SESSION_EXPIRED);
        }

        session.setAttribute("adminAuthExpiry", LocalDateTime.now().plusMinutes(10));

        return ResponseEntity.ok(ApiResponse.success(200, "세션 연장 성공", null));
    }


}
