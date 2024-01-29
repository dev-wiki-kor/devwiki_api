package com.dk0124.project.user.application.service;

import com.dk0124.project.global.config.security.csrf.CsrfTokenRegister;
import com.dk0124.project.global.config.security.session.LoginSessionRegister;
import com.dk0124.project.user.domain.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;


/**
 * 로그인 성공 시 수행되는 핸들러 클래스
 *
 * 주요 역할:
 * - 로그인 세션을 등록하는 기능
 * - CSRF 토큰을 등록하는 기능
 */
@Service
@RequiredArgsConstructor
public class LoginSuccessHandler {

    private final LoginSessionRegister loginSessionRegister;
    private final CsrfTokenRegister csrfTokenRegister;

    private final HttpServletRequest httpServletRequest;
    private final HttpServletResponse httpServletResponse;


    /**
     * 로그인 성공 시 세션을 생성하고 CSRF 토큰을 등록함.
     *
     * @param userId    로그인한 사용자의 ID (메타 유저 아이디 )
     * @param userRoles Set<UserRole>
     */
    public void createSession(Long userId, Set<UserRole> userRoles ){
        loginSessionRegister.registerLoginSession(userId,userRoles);
        // CSRF 토큰 등록
        csrfTokenRegister.registerToken(httpServletRequest, httpServletResponse);
    }
}
