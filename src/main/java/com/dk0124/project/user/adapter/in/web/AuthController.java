package com.dk0124.project.user.adapter.in.web;

import com.dk0124.project.user.adapter.in.web.request.LoginRequest;
import com.dk0124.project.user.adapter.in.web.request.SignInRequest;
import com.dk0124.project.user.application.port.in.LoginUsercase;
import com.dk0124.project.user.application.port.in.SignInUsecase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUsercase loginUsercase;
    private final SignInUsecase signInUsecase;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        var userLoginInfo = loginUsercase.login(loginRequest.userName(), loginRequest.password());
        return ResponseEntity.ok(userLoginInfo.getUserName());
    }

    @PostMapping("/signIn")
    public ResponseEntity<String> signIn(@RequestBody SignInRequest signInRequest) {
        signInUsecase.signIn(signInRequest.userName(), signInRequest.password());
        return ResponseEntity.ok("ok");
    }
}
