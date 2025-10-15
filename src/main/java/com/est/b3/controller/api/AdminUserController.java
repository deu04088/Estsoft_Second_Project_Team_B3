package com.est.b3.controller.api;

import com.est.b3.domain.Boss;
import com.est.b3.dto.admin.AdminUserResponse;
import com.est.b3.dto.api.ApiResponse;
import com.est.b3.exception.CustomException;
import com.est.b3.exception.ErrorCode;
import com.est.b3.repository.BossRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final BossRepository bossRepository;

    /**
     * 유저 전체 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<AdminUserResponse>>> getAllUsers() {

        List<Boss> bosses = bossRepository.findAll();

        List<AdminUserResponse> responses = bosses.stream()
                .map(AdminUserResponse::new)
                .toList();

        return ResponseEntity.ok(ApiResponse.success(200, "유저 목록 조회 성공", responses));
    }


    /**
     * 유저 상태 토글 (1=활성 → 0=탈퇴, 0=탈퇴 → 1=활성)
     */
    @PutMapping("/{id}/toggle")
    public ResponseEntity<ApiResponse<Void>> toggleUserState(@PathVariable Long id) {

        Boss boss = bossRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        boss.toggleState();

        bossRepository.save(boss);

        String message = boss.getState() == 1 ? "회원이 복구되었습니다." : "회원이 탈퇴 처리되었습니다.";

        return ResponseEntity.ok(ApiResponse.success(200, message, null));
    }
}
