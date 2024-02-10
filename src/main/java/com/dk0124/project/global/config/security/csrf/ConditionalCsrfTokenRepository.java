package com.dk0124.project.global.config.security.csrf;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

/*
* 커스텀 csrf 토큰 레포지토리
* 유저 로그인 세션이 있는 경우에만, csrf 토큰을 발급.
*
* 로그인 성공 시, CsrfTokenRegister 를 직접 호출해서 body 에 토큰을 담아서 전달.
* */
public class ConditionalCsrfTokenRepository implements CsrfTokenRepository{

    private CsrfTokenRepository delegatedRepository = new HttpSessionCsrfTokenRepository();

    // 로그인 인증된 유저에 대해서만 csrf 토큰 발급
    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        return isAuthenticated(request) ? delegatedRepository .generateToken(request) : null;
    }

    // csrf 값을 바꾸지 않음 .
    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        if (token != null) {
            delegatedRepository.saveToken(token, request, response);
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        var header = request.getHeader("X-CSRF-TOKEN");
        var headers = request.getHeaderNames();
        return delegatedRepository.loadToken(request);
    }

    // 로그인 인증된 유저에 대해서만 csrf 토큰 발급
    private boolean isAuthenticated(HttpServletRequest request) {
        // 사용자 세션이 인증된 상태인지 확인합니다.
        return request.getSession(false) != null &&
                request.getSession().getAttribute("USER_INFO") != null;
    }
}
