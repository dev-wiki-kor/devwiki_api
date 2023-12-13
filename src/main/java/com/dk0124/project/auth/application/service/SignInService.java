package com.dk0124.project.auth.application.service;

import com.dk0124.project.auth.application.port.in.SignInUsecase;
import com.dk0124.project.auth.application.port.out.CreateUserPort;
import com.dk0124.project.auth.application.port.out.CreateUserPortCommand;
import com.dk0124.project.auth.application.port.out.UserExistPort;
import com.dk0124.project.auth.domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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

        createUserPort.createUser(
                new CreateUserPortCommand(userName, passwordEncoder.encode(password), Set.of(UserRole.USER))
        );
    }
}
