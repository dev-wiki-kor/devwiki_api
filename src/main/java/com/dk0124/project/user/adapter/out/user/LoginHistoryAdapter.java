package com.dk0124.project.user.adapter.out.user;


import com.dk0124.project.user.adapter.out.user.entity.UserLoginHistoryEntity;
import com.dk0124.project.user.adapter.out.user.repository.UserLoginHistoryEntityRepository;

import com.dk0124.project.user.application.port.out.LoginHistoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoginHistoryAdapter implements LoginHistoryPort {

    private final UserLoginHistoryEntityRepository userLoginHistoryEntityRepository;

    @Override
    public void writeLoginHistory(Long userId) {
        userLoginHistoryEntityRepository.save(
                UserLoginHistoryEntity.of(
                        userId,
                        UserActionType.LOGIN,
                        LocalDateTime.now()
                )
        );
    }
}
