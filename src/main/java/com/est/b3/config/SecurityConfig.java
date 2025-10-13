package com.est.b3.config;

import com.est.b3.config.security.CustomOAuth2UserService;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 전체 설정
 * - 세션 기반 로그인 유지
 * - 구글 OAuth2 로그인 연동 (.env 기반 client-id/secret)
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService; // ✅ OAuth2UserService 주입
    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        String clientId = dotenv.get("GOOGLE_CLIENT_ID");
        String clientSecret = dotenv.get("GOOGLE_CLIENT_SECRET");

        http
                // 1. CSRF 비활성화 (개발 중 편의)
                .csrf(AbstractHttpConfigurer::disable)

                // 2. H2-console 접근 허용 (frame 옵션 해제)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // 3. 요청별 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/login", "/signup",
                                "/h2-console/**",
                                "/css/**", "/js/**", "/images/**", "/uploads/**",
                                // REST 로그인, 회원가입 API는 인증 없이 허용
                                "/api/bosses/login", "/api/bosses/signup",
                                // OAuth2 로그인 관련 경로
                                "/oauth2/**"
                        ).permitAll()
                        // 나머지는 모두 인증 필요
                        .anyRequest().permitAll()
                )

                // 4. OAuth2 로그인 설정
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // 사용자 정의 로그인 페이지
                        .defaultSuccessUrl("/oauth2/success", true) // 로그인 성공 시 이동
                        .failureUrl("/login?error=true") // 로그인 실패 시 이동
                        .clientRegistrationRepository(
                                new InMemoryClientRegistrationRepository(
                                        CustomGoogleClientRegistration.build(clientId, clientSecret)
                                )
                        )
                        // 구글 사용자 정보 처리 서비스 연결
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                )

                // 5. 기본 로그인(FormLogin) / HTTP Basic 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)

                // 6️. 세션 정책 - 로그인 유지
                .sessionManagement(session -> session
                        .maximumSessions(1) // 동시 로그인 1개 제한
                        .maxSessionsPreventsLogin(false)
                );


        return http.build();
    }

    // 비밀번호 암호화용 Bean
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
