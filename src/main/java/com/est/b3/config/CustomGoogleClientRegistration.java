package com.est.b3.config;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistration.Builder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

public class CustomGoogleClientRegistration {

    public static ClientRegistration build(String clientId, String clientSecret) {
        return ClientRegistration.withRegistrationId("google") // "google" 로그인으로 식별
                .clientId(clientId) // .env에서 불러온 값
                .clientSecret(clientSecret)
                .scope("profile", "email") // 요청 권한
                .authorizationUri("https://accounts.google.com/o/oauth2/v2/auth") // 구글 인증 URL
                .tokenUri("https://oauth2.googleapis.com/token") // 토큰 요청 URL
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo") // 사용자 정보 요청 URL
                .userNameAttributeName("sub") // 구글의 사용자 고유 ID
                .clientName("Google")
                .redirectUri("{baseUrl}/login/oauth2/code/google") // OAuth2 표준 리디렉트 경로
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .build();
    }
}
