package com.dk0124.project.common.user.adapter.out.user;

import com.dk0124.project.common.user.adapter.out.user.repository.UserGithubInfoEntityRepository;
import com.dk0124.project.common.user.adapter.out.user.repository.UserMetaEntityRepository;
import com.dk0124.project.common.user.application.port.out.UserExistCheckPort;

import com.dk0124.project.common.user.exception.UserNotExistException;
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
