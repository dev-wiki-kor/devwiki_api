package com.dk0124.project.global.config.security.csrf;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

/*
* 로그인 성공 시, CsrfTokenRegister 를 직접 호출해서 body 에 토큰을 담아서 전달.
* */
@Component
@RequiredArgsConstructor
public class CsrfTokenRegister {
    private final ConditionalCsrfTokenRepository conditionalCsrfTokenRepository;

    public void registerToken(HttpServletRequest request, HttpServletResponse response) {
        CsrfToken csrfToken = conditionalCsrfTokenRepository.generateToken(request);
        conditionalCsrfTokenRepository.saveToken(csrfToken, request, response);
    }

}
