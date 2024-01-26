package com.dk0124.project.user.adapter.out.user;

import com.dk0124.project.user.adapter.out.user.entity.UserStatus;
import com.dk0124.project.user.domain.UserGithubInfo;
import com.dk0124.project.user.adapter.out.user.repository.UserGithubInfoEntityRepository;
import com.dk0124.project.user.adapter.out.user.repository.UserMetaEntityRepository;

import com.dk0124.project.user.application.port.out.UserExistCheckPort;
import com.dk0124.project.user.exception.UserNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserExistCheckAdapter implements UserExistCheckPort {

    private final UserGithubInfoEntityRepository userGithubInfoEntityRepository;

    private final UserMetaEntityRepository userMetaEntityRepository;

    @Override
    public UserGithubInfo findByGithubUniqueId(String githubUniqueId) {
        var userGithubInfo = userGithubInfoEntityRepository.findByGithubUniqueId(githubUniqueId)
                .orElseThrow(()-> new UserNotExistException());

        var userInfo = userMetaEntityRepository.findByIdAndActive(userGithubInfo.getUserMetaId(), true)
                .orElseThrow(()-> new UserNotExistException());


        return UserGithubInfo.of(
                userGithubInfo.getId(),
                userGithubInfo.getUserMetaId(),
                userGithubInfo.getGithubUniqueId(),
                userInfo.getUserRoles(),
                userInfo.isActive()
        );
    }


}
