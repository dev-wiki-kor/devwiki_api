package com.dk0124.project.config.security;


import com.dk0124.project.config.security.authProvider.SessionBasedAuthFilter;
import com.dk0124.project.config.security.csrf.ConditionalCsrfTokenRepository;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer;



import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Spring Security 설정 클래스
 *
 * - 사용하지 않는 기능 비활성화 ( 로그인 , 리맴버미)
 * - CSRF 보호를 위한 CORS 구성
 * - 로그인 성공 시 CSRF 토큰을 응답 본문으로 전달하기 위한 커스텀 CSRF 토큰 저장소 등록
 * - 세션 기반 인증을 위한 커스텀 필터 등록
 * - 로그인, 가입 기능에 대해서 CSRF 및 인증 필터 적용 제외
 */
@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {


    /**
     * CORS 구성을 위한 빈 등록
     *
     * @return CORS 구성
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3001", "https://devwiki.org")); // localhost:3000 및 하위 경로 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메소드
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "X-CSRF-TOKEN")); // 허용할 헤더
        configuration.setAllowCredentials(true); // 쿠키 및 인증 관련 헤더 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 해당 설정 적용
        return source;
    }

    /**
     * Same-Site 속성 설정
     *
     * @return Same-Site 속성 설정
     */
    @Bean
    public CookieSameSiteSupplier applicationCookieSameSiteSupplier() {
        return CookieSameSiteSupplier.ofLax();
    }

    /**
     * Spring Security 필터 체인 구성
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Form 로그인 및 HTTP 기본 인증 비활성화
                .httpBasic(HttpBasicConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .rememberMe(RememberMeConfigurer::disable)
                // CSRF 토큰 저장소 설정
                .csrf((csrf) -> csrf
                        .csrfTokenRepository(conditionalCsrfTokenRepository())
                        .ignoringRequestMatchers("/v1/user/**")
                )
                // 기본 CORS 설정 적용
                .cors(Customizer.withDefaults())

                // 세션 기반 인증을 위한 커스텀 필터 등록
                .addFilterBefore(new SessionBasedAuthFilter(), RequestHeaderAuthenticationFilter.class)

                // 로그인, 가입, 세션 관련 기능에 대한 CSRF 및 인증 필터 제외 설정
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/v1/user/**").permitAll()
                        .anyRequest().authenticated()
                )

                // 커스텀 예외 메시지 설정
                .exceptionHandling((exception) -> exception
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )

        ;
        return http.build();
    }

    /**
     * 커스텀 CSRF 토큰 저장소 빈 등록
     * */
    @Bean
    public ConditionalCsrfTokenRepository conditionalCsrfTokenRepository() {
        return new ConditionalCsrfTokenRepository();
    }


}
