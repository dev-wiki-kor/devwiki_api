package com.dk0124.project.config.session.loginSession;

import com.dk0124.project.user.domain.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;

/*
* FOR TEST
* */
@Service
@Slf4j
public class LoginSessionService {

    @Value("${app.var.login-session-user-attribute}")
    private String USER_INFO;

    @Autowired
    private HttpServletRequest request;

    public void registerLoginSession(Long userId, Set<UserRole> roles) {
        LoginSession loginSession = new LoginSession(userId, roles);
        HttpSession session = request.getSession(true); // 세션 새로 생성
        session.setAttribute(USER_INFO, loginSession); // 세션에 LoginSession 객체 저장
    }

    public void readSession() {
        HttpSession session = request.getSession(false);
        LoginSession userInfo = (LoginSession) session.getAttribute(USER_INFO);
        log.info("new session id : {}", session.getId());
    }
}
