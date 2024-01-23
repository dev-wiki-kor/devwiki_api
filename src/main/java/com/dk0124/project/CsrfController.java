package com.dk0124.project;

import lombok.RequiredArgsConstructor;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/*
* Call and save CSRF token when front js code up
* */
@RestController
@RequestMapping("/csrf")
@RequiredArgsConstructor
public class CsrfController {

    @GetMapping("/get")
    public CsrfToken csrf(CsrfToken csrfToken) {
        return csrfToken;
    }
}