package com.dk0124.project.user.application.service;

import com.dk0124.project.config.security.csrf.CsrfTokenRegister;
import com.dk0124.project.config.security.session.LoginSessionRegister;
import com.dk0124.project.user.domain.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class LoginSuccessHandler {

    private final LoginSessionRegister loginSessionRegister;
    private final CsrfTokenRegister csrfTokenRegister;

    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;


    public void createSession(Long userId, Set<UserRole> userRoles ){
        loginSessionRegister.registerLoginSession(userId,userRoles);
        // CSRF 토큰 등록
        csrfTokenRegister.registerToken(httpServletRequest, httpServletResponse);
    }
}
