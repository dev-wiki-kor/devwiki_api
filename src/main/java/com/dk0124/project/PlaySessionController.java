package com.dk0124.project;


import com.dk0124.project.config.security.csrf.ConditionalCsrfTokenRepository;
import com.dk0124.project.config.security.session.LoginSessionRegister;

import com.dk0124.project.user.application.service.LoginSuccessHandler;
import com.dk0124.project.user.domain.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


/*
 * 보안 구현 중, 테스트 api
 * */
@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
@Slf4j
public class PlaySessionController {

    private final LoginSessionRegister loginSessionRegister;

    private final LoginSuccessHandler loginSuccessHandler;

    private final ConditionalCsrfTokenRepository conditionalCsrfTokenRepository;

    private final HttpServletRequest httpServletRequest;

    @GetMapping("/create")
    public String create() {
        loginSessionRegister.registerLoginSession(1L, Set.of(UserRole.USER));
        return "created";
    }

    @PostMapping
    @RequestMapping("/success")
    private CsrfToken loginSuccess(){
        loginSuccessHandler.createSession(1L, Set.of(UserRole.ADMIN));

        return conditionalCsrfTokenRepository.loadToken(httpServletRequest);

    }
}
