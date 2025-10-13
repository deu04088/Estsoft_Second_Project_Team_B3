package com.est.b3.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final Dotenv dotenv = Dotenv.load();


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        String clientId = dotenv.get("GOOGLE_CLIENT_ID");
        String clientSecret = dotenv.get("GOOGLE_CLIENT_SECRET");

        http
                // CSRF 비활성화
                .csrf(AbstractHttpConfigurer::disable)

                // H2-console 사용을 위한 frame 옵션 해제
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // 요청 권한 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll() // h2-console 열기
                    .anyRequest().permitAll()
                )

                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/oauth2/success", true)
                        .failureUrl("/login?error=true")
                        .clientRegistrationRepository(
                                new InMemoryClientRegistrationRepository(
                                        CustomGoogleClientRegistration.build(clientId, clientSecret)
                                )
                        )
                )

                // 기본 로그인/HTTP Basic 비활성화
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable);



        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
