package com.dk0124.project.config.security.csrf;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CsrfTokenRegister {
    private final ConditionalCsrfTokenRepository conditionalCsrfTokenRepository;

    public void registerToken(HttpServletRequest request, HttpServletResponse response) {
        CsrfToken csrfToken = conditionalCsrfTokenRepository.generateToken(request);
        conditionalCsrfTokenRepository.saveToken(csrfToken, request, response);
    }

}
