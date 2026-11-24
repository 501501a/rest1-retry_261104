package com.rest1.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // ① 계약서
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationFilter customAuthenticationFilter;

    @Bean
        // ② 모든 요청에 적용
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // ③ 위에서부터 순서대로 검사
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**").permitAll()
                        // "인증 x" → permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/*/posts", "/api/*/posts/{id:\\d+}",
                                "/api/*/posts/{postId:\\d+}/comments", "/api/*/posts/{postId:\\d+}/comments/{commentId:\\d+}").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v1/members/login", "/api/v1/members").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/members/logout").permitAll()
                        .anyRequest().authenticated()) // ④ 나머지는 전부 로그인 필요
                .csrf(csrf -> csrf.disable()) // ⑤ 쿠키 세션 방식이 아니라서 끔
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())) // ⑥ h2 콘솔 화면 허용
                        .addFilterBefore(customAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}