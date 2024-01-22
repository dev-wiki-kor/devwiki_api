package com.dk0124.project.config.security;


/*
 * 1. csrf
 *
 * */

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.CookieSameSiteSupplier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.annotation.web.configurers.RememberMeConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    // same-site attribute of LAX for CSRF
    /*
    @Bean
    public CookieSameSiteSupplier applicationCookieSameSiteSupplier() {
        return CookieSameSiteSupplier.ofLax();
    }
     */


    // general security config
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // dont use form login
                .httpBasic(HttpBasicConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .rememberMe(RememberMeConfigurer::disable)

                // 테스트 중 csrf disabled 가 없다면 , post 요청에서 403 forbidden 을 반환한다..
                .csrf(CsrfConfigurer::disable)
                // can be corsed
                .cors(Customizer.withDefaults())
                // dont use default session
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // auth applied by session in JSESSIONID attr user-info
                .addFilterBefore(new SessionBasedAuthFilter(), RequestHeaderAuthenticationFilter.class)
                // only login, logout, signIn can be accessed without login session
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/v1/user/login").permitAll()
                        .requestMatchers("/v1/user/signIn").permitAll()
                        .requestMatchers(("/session/**")).permitAll()
                        .anyRequest().authenticated()
                )


                // cutom exception handling
                .exceptionHandling((exception) -> exception
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
                        .accessDeniedHandler(new CustomAccessDeniedHandler())
                )
        ;
        return http.build();
    }


}
