package com.est.b3.config.security;

import com.est.b3.domain.Boss;
import com.est.b3.dto.SessionUserDTO;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 구글 로그인 성공 후 리다이렉트 페이지
 * - 세션에 사용자 정보(SessionUserDTO) 저장
 * - 주소 등록 여부에 따라 페이지 분기
 */
@Controller
@RequiredArgsConstructor
public class OAuth2SuccessController {

    @GetMapping("/oauth2/success")
    public String success(
            @AuthenticationPrincipal CustomOAuth2User oauthUser,
            HttpSession session
    ) {
        Boss boss = oauthUser.getBoss();

        // 세션 저장
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

        // 주소 유무에 따라 분기
        if (boss.getAddress() == null) {
            return "redirect:/address-certify";
        } else {
            return "redirect:/restaurants";
        }
    }
}
