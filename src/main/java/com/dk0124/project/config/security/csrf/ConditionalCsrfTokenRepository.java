package com.dk0124.project.config.security.csrf;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

/*
* csrf 토큰을 로그인 성공 시 https 바디로 전송.
* */
public class ConditionalCsrfTokenRepository implements CsrfTokenRepository{

    private CsrfTokenRepository delegatedRepository = new HttpSessionCsrfTokenRepository();

    // 로그인 인증된 유저에 대해서만 csrf 토큰 발급
    @Override
    public CsrfToken generateToken(HttpServletRequest request) {
        return isAuthenticated(request) ? delegatedRepository .generateToken(request) : null;
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse response) {
        if (token != null) {
            delegatedRepository.saveToken(token, request, response);
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest request) {
        return delegatedRepository .loadToken(request);
    }

    // 로그인 인증된 유저에 대해서만 csrf 토큰 발급
    private boolean isAuthenticated(HttpServletRequest request) {
        // 사용자 세션이 인증된 상태인지 확인합니다.
        return request.getSession(false) != null &&
                request.getSession().getAttribute("USER_INFO") != null;
    }
}
