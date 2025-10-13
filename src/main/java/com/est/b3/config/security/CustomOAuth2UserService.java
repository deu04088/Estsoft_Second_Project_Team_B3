package com.est.b3.config.security;

import com.est.b3.domain.Boss;
import com.est.b3.repository.BossRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * 구글 로그인 성공 후 사용자 정보를 가져와
 * Boss DB에 저장(또는 기존 유저 조회)하는 역할
 */
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final BossRepository bossRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest)
            throws OAuth2AuthenticationException {

        // Google에서 사용자 정보 가져오기
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 구글에서 제공하는 프로필 정보
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        // DB에서 유저 존재 확인
        Boss boss = bossRepository.findByUserName(email)
                .orElseGet(() -> bossRepository.save(
                        Boss.builder()
                                .userName(email)
                                .nickName(name)
                                .password("") // 구글 로그인은 비밀번호 없음
                                .role("ROLE_USER")
                                .state(1)
                                .build()
                ));

        // OAuth2User 구현체로 래핑
        return new CustomOAuth2User(boss, oAuth2User.getAttributes());
    }
}
