package com.dk0124.project.auth.adapter.out;

import com.dk0124.project.auth.adapter.out.repository.UserEntityRepository;
import com.dk0124.project.auth.adapter.out.repository.UserPasswordEntityRepository;
import com.dk0124.project.auth.adapter.out.repository.UserRoleEntityRepository;
import com.dk0124.project.auth.application.port.out.LoginPort;
import com.dk0124.project.auth.domain.UserLoginInfo;
import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserLoginInfoAdapter implements LoginPort {
    private final UserEntityRepository userEntityRepository;
    private final UserPasswordEntityRepository userPasswordEntityRepository;
    private final UserRoleEntityRepository userRoleEntityRepository;

    public UserLoginInfo getLoginInfo(String userName) {

        var userEntity = userEntityRepository.findByUserName(userName)
                .orElseThrow(() -> new NoResultException());

        var passwordEntity = userPasswordEntityRepository.findByUserId(userEntity.getId())
                .orElseThrow(() -> new NoResultException());

        var userRoles = userRoleEntityRepository.findAllByUserId(userEntity.getId())
                .stream().map(e -> e.userRole).collect(Collectors.toSet());

        return UserLoginInfo.of(userEntity.getId(), userEntity.getUserName(), passwordEntity.getPassword(), userRoles);
    }
}
