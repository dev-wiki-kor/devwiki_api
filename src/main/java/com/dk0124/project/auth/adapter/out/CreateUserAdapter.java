package com.dk0124.project.auth.adapter.out;

import com.dk0124.project.auth.adapter.out.entity.UserEntity;
import com.dk0124.project.auth.adapter.out.entity.UserPasswordEntity;
import com.dk0124.project.auth.adapter.out.entity.UserProfileEntity;
import com.dk0124.project.auth.adapter.out.entity.UserRoleEntity;
import com.dk0124.project.auth.adapter.out.repository.UserEntityRepository;
import com.dk0124.project.auth.adapter.out.repository.UserPasswordEntityRepository;
import com.dk0124.project.auth.adapter.out.repository.UserProfileEntityRespository;
import com.dk0124.project.auth.adapter.out.repository.UserRoleEntityRepository;
import com.dk0124.project.auth.application.port.out.CreateUserPort;
import com.dk0124.project.auth.application.port.out.CreateUserPortCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CreateUserAdapter implements CreateUserPort {

    private final UserEntityRepository userEntityRepository;
    private final UserPasswordEntityRepository userPasswordEntityRepository;
    private final UserProfileEntityRespository userProfileEntityRespository;
    private final UserRoleEntityRepository userRoleEntityRepository;


    @Override
    public void createUser(CreateUserPortCommand createUserCommand) {
        // save user entity
        var userEntity = userEntityRepository.save(new UserEntity(createUserCommand.userName()));
        // save user pw
        userPasswordEntityRepository.save(new UserPasswordEntity(userEntity.getId(), createUserCommand.password()));
        // save user profile
        userProfileEntityRespository.save(new UserProfileEntity(userEntity.getId()));
        // save user role
        userRoleEntityRepository.saveAll(
                createUserCommand.userRole().stream()
                        .map(e -> new UserRoleEntity(userEntity.getId(), e))
                        .collect(Collectors.toList())
        );
    }

}
