package com.est.b3.service;

import com.est.b3.domain.Boss;
import com.est.b3.dto.*;
import com.est.b3.exception.CustomException;
import com.est.b3.exception.ErrorCode;
import com.est.b3.repository.BossRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;

@Service
@RequiredArgsConstructor
public class BossService {

    private final BossRepository bossRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    /**
     * 회원가입
     */
    public SignupResponse signup(SignupRequest request) {
        // 아이디 중복 검사
        if (bossRepository.existsByUserName(request.getUserName())) {
            throw new CustomException(ErrorCode.USERNAME_DUPLICATE); // 필요하면 ErrorCode.USERNAME_DUPLICATE 추가
        }

        // 닉네임 중복 검사
        if (bossRepository.existsByNickName(request.getNickName())) {
            throw new CustomException(ErrorCode.NICKNAME_DUPLICATE); // 필요하면 ErrorCode.NICKNAME_DUPLICATE 추가
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Boss 엔티티 생성 및 저장
        Boss boss = Boss.builder()
                .userName(request.getUserName())
                .nickName(request.getNickName())
                .password(encodedPassword)
                .role("ROLE_USER")
                .state(1) // 기본 활성 상태
                .build();

        Boss saved = bossRepository.save(boss);

        return new SignupResponse(saved.getId(), saved.getUserName(), saved.getNickName());
    }

    /**
     * 로그인
     */
    public LoginResponse login(LoginRequest request, HttpSession session) {

        Boss boss = bossRepository.findByUserName(request.getUserName())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), boss.getPassword())) {
            throw new CustomException(ErrorCode.PASSWORD_MISMATCH); // 필요하면 ErrorCode.PASSWORD_MISMATCH 추가
        }

        // 로그인 응답 DTO (주소 포함 → 프론트에서 분기 처리용)
        LoginResponse loginResponse = new LoginResponse(
                boss.getId(),
                boss.getUserName(),
                boss.getNickName(),
                boss.getAddress()
        );

        // 세션에는 주소 제외한 최소 정보만 저장 -> 수정사항 : address 추가
        SessionUserDTO sessionUser = new SessionUserDTO(
                boss.getId(),
                boss.getUserName(),
                boss.getNickName(),
                boss.getAddress(),
                boss.getSiDo(),
                boss.getGuGun(),
                boss.getDongEupMyeon()
        );

        session.setAttribute("loginBoss", sessionUser);


        /*
         [나중 Security 적용 시 이 부분을 교체]
         Authentication authentication = new UsernamePasswordAuthenticationToken(
                 boss, null, Collections.singletonList(new SimpleGrantedAuthority(boss.getRole()))
         );
         SecurityContextHolder.getContext().setAuthentication(authentication);
        */

        return loginResponse;
    }

    /**
     * 로그아웃
     */
    public void logout(HttpSession session) {
        if (session.getAttribute("loginBoss") == null) {
            throw new CustomException(ErrorCode.INVALID_REQUEST); // 유효하지 않은 세션
        }

        // [현재 방식] 세션 무효화
        session.invalidate();

        /*
         [나중 Security 적용 시 이 부분을 교체]
         SecurityContextHolder.clearContext();
        */
    }
}
