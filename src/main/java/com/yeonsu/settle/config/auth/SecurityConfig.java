package com.yeonsu.settle.config.auth;

import com.yeonsu.settle.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)

            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))  // H2 콘솔에 대한 Frame 옵션을 비활성화

            .authorizeHttpRequests(auth -> auth  // authorizeRequests()는 authorizeHttpRequests()로 변경
                    .requestMatchers(
                        new AntPathRequestMatcher("/"),
                        new AntPathRequestMatcher("/css/**"),
                        new AntPathRequestMatcher("/images/**"),
                        new AntPathRequestMatcher("/js/**"),
                        new AntPathRequestMatcher("/h2-console/**"),
                        new AntPathRequestMatcher("/profile")
                    ).permitAll()  // 해당 경로는 모든 사용자에게 허용
                    .requestMatchers(new AntPathRequestMatcher("/api/v1/**")
                    ).hasRole(Role.USER.name())  // "/api/v1/**" 경로는 USER 권한을 가진 사용자만 접근 가능
                    .anyRequest().authenticated()  // 나머지 모든 요청은 인증된 사용자만 접근 가능
            )

            .logout(logout -> logout.logoutSuccessUrl("/"))  // 로그아웃 성공 시 "/"로 리다이렉트

            .oauth2Login(oauth2 -> oauth2
                .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                    .userService(customOAuth2UserService)));  // OAuth2 로그인 설정

        return http.build();
    }
}