package com.dk0124.project.auth.adapter.out;

import com.dk0124.project.auth.adapter.out.repository.UserEntityRepository;
import com.dk0124.project.auth.application.port.out.UserExistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserExistAdapter implements UserExistPort {

    private final UserEntityRepository userEntityRepository;

    public boolean existByUserName(String userName) {
        return userEntityRepository.existsByUserName(userName);
    }

    public boolean existByUserId(Long userId) {
        return userEntityRepository.existsById(userId);
    }
}
