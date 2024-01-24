package com.dk0124.project.config.security;


import com.dk0124.project.config.security.authProvider.SessionBasedAuthFilter;
import lombok.RequiredArgsConstructor;
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
import org.springframework.security.web.csrf.*;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3001")); // localhost:3000 및 하위 경로 허용
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메소드
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "X-CSRF-TOKEN" )); // 허용할 헤더
        configuration.setAllowCredentials(true); // 쿠키 및 인증 관련 헤더 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 대해 해당 설정 적용
        return source;
    }

    // same-site attribute of LAX for CSRF
    @Bean
    public CookieSameSiteSupplier applicationCookieSameSiteSupplier() {
        return CookieSameSiteSupplier.ofLax();
    }

    // general security config
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http
                // dont use form login
                .httpBasic(HttpBasicConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .rememberMe(RememberMeConfigurer::disable)
                // csrf token is not generated automatically in spring security v6, this initial token can get from CsrfController .
                .csrf((csrf) -> csrf
                        .csrfTokenRequestHandler(requestHandler)
                )
                // default cors setting
                .cors(Customizer.withDefaults())

                // security context go with session in JSESSIONID attr user-info
                .addFilterBefore(new SessionBasedAuthFilter(), RequestHeaderAuthenticationFilter.class)

                // only login, logout, signIn can be accessed without login session
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/v1/user/login").permitAll()
                        .requestMatchers("/v1/user/signIn").permitAll()
                        // for manual csrf token register
                        .requestMatchers("/csrf/**").permitAll()
                        .requestMatchers(("/session/**")).permitAll()
                        .anyRequest().authenticated()
                )

                // cutom exception message
                .exceptionHandling((exception) -> exception
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
        ;
        return http.build();
    }


}
