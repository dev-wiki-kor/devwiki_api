package com.dk0124.project.auth.adapter.in.web;

import com.dk0124.project.auth.adapter.in.web.request.LoginRequest;
import com.dk0124.project.auth.adapter.in.web.request.SignInRequest;
import com.dk0124.project.auth.application.port.in.LoginUsercase;
import com.dk0124.project.auth.application.port.in.SignInUsecase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUsercase loginUsercase;
    private final SignInUsecase signInUsecase;

    // TODO : sessionid 상수화 / session 자료형 클래스로 생성.

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletRequest servletRequest, @RequestBody LoginRequest loginRequest) {
        var userLoginInfo = loginUsercase.login(loginRequest.userName(), loginRequest.password());
        HttpSession session = servletRequest.getSession();
        session.setAttribute("JSESSIONID", userLoginInfo.getUserName());
        return ResponseEntity.ok(userLoginInfo.getUserName());
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest signInRequest) {
        signInUsecase.signIn(signInRequest.userName(), signInRequest.password());
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello(HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        var data = (String) session.getAttribute("JSESSIONID");
        return ResponseEntity.ok("hello");
    }
}
