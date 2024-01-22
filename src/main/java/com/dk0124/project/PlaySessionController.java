package com.dk0124.project;


import com.dk0124.project.config.security.loginSession.LoginSessionService;
import com.dk0124.project.user.domain.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/session")
@Slf4j
public class PlaySessionController {

    private final LoginSessionService loginSessionService;

    @GetMapping("/create")
    public String create() {
        loginSessionService.registerLoginSession(1L, Set.of(UserRole.USER));
        return "created";
    }


    @GetMapping("/read")
    public String read() {
        loginSessionService.readSession();
        return "ok";
    }

}
