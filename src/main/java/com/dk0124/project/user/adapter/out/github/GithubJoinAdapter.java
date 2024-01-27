package com.dk0124.project.user.adapter.out.github;

import com.dk0124.project.user.adapter.out.user.entity.UserGithubInfoEntity;
import com.dk0124.project.user.adapter.out.user.entity.UserMetaEntity;
import com.dk0124.project.user.adapter.out.user.entity.UserProfileEntity;
import com.dk0124.project.user.adapter.out.user.repository.UserGithubInfoEntityRepository;
import com.dk0124.project.user.adapter.out.user.repository.UserMetaEntityRepository;
import com.dk0124.project.user.adapter.out.user.repository.UserProfileEntityRepository;
import com.dk0124.project.user.application.port.out.GithubUserJoinPort;
import com.dk0124.project.user.domain.JoinCommand;
import com.dk0124.project.user.domain.UserRole;
import com.dk0124.project.user.domain.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class GithubJoinAdapter implements GithubUserJoinPort {

    private final UserGithubInfoEntityRepository userGithubInfoEntityRepository;

    private final UserProfileEntityRepository userProfileEntityRepository;

    private final UserMetaEntityRepository userMetaEntityRepository;

    @Override
    public boolean isUniqueIdAvailable(String uniqueId) {
        return !userGithubInfoEntityRepository.existsByGithubUniqueId(uniqueId);
    }

    @Override
    public boolean isNickNameAvailable(String nickname) {
        return !userProfileEntityRepository.existsByNickname(nickname);
    }


    @Override
    public void join(JoinCommand joinCommand) {
        // 일반 사용가능한 유저 설정 .
        var userMetaEntity
                = userMetaEntityRepository.save(UserMetaEntity.of(Set.of(UserRole.USER), Set.of(UserStatus.NORMAL), true));


        userProfileEntityRepository.save(UserProfileEntity.of(
                userMetaEntity.getId(),
                null,
                joinCommand.nickname()
        ));

        userGithubInfoEntityRepository.save(UserGithubInfoEntity.of(
                userMetaEntity.getId(),
                joinCommand.githubUniqueId(),
                joinCommand.email(),
                joinCommand.profileUrl(),
                true
        ));
    }
}
