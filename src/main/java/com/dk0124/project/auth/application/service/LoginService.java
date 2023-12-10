package com.dk0124.project.auth.application.service;

import com.dk0124.project.auth.application.port.in.LoginUsercase;
import com.dk0124.project.auth.application.port.out.LoginPort;
import com.dk0124.project.auth.domain.UserLoginInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService implements LoginUsercase {
    private final LoginPort loginPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserLoginInfo login(String userName, String password) {
        var loginInfo = loginPort.getLoginInfo(userName, passwordEncoder.encode(password));

        if (!passwordEncoder.matches(password, loginInfo.getPassword()))
            throw new RuntimeException("패스워드 불일치");

        return loginInfo;
    }
}
