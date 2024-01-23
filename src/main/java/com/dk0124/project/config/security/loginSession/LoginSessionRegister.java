package com.dk0124.project.config.security.loginSession;

import com.dk0124.project.user.domain.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;

/*
* FOR TEST
* */
@Component
@Slf4j
@RequiredArgsConstructor
public class LoginSessionRegister {

    @Value("${app.var.login-session-user-attribute}")
    private String USER_INFO;

    private final HttpServletRequest request;

    public void registerLoginSession(Long userId, Set<UserRole> roles) {
        LoginSession loginSession = new LoginSession(userId, roles);
        HttpSession session = request.getSession(true); // 세션 새로 생성
        session.setAttribute(USER_INFO, loginSession); // 세션에 LoginSession 객체 저장
    }
}
