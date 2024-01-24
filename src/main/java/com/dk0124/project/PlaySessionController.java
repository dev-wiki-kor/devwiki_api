package com.dk0124.project;


import com.dk0124.project.config.security.loginSession.LoginSessionRegister;

import com.dk0124.project.user.domain.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/create")
    public String create() {
        loginSessionRegister.registerLoginSession(1L, Set.of(UserRole.USER));
        return "created";
    }
}
