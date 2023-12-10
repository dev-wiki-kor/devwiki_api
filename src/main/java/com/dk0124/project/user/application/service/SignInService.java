package com.dk0124.project.user.application.service;

import com.dk0124.project.user.application.port.in.SignInUsecase;
import com.dk0124.project.user.application.port.out.CreateUserPort;
import com.dk0124.project.user.application.port.out.UserExistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SignInService implements SignInUsecase {

    private final PasswordEncoder passwordEncoder;
    private final UserExistPort userExistPort;
    private final CreateUserPort createUserPort;

    @Override
    @Transactional
    public void signIn(String userName, String password) {

        if (userExistPort.existByUserName(userName))
            throw new RuntimeException("this name is already taken");

        createUserPort.createUser(userName, passwordEncoder.encode(password));
    }
}
