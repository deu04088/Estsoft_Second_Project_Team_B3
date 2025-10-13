package com.est.b3.config.security;

import com.est.b3.domain.Boss;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * OAuth2User 구현체
 * - 구글에서 받은 사용자 정보 + 우리 시스템 B3의 Boss 엔티티 정보를 함께 보관
 */

@Getter
public class CustomOAuth2User implements OAuth2User {

    private final Boss boss;
    private final Map<String, Object> attributes;

    public CustomOAuth2User(Boss boss, Map<String, Object> attributes) {
        this.boss = boss;
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(boss.getRole()));
    }

    @Override
    public String getName() {
        // OAuth2User 인터페이스에서 요구하는 기본 이름
        return boss.getUserName();
    }
}
