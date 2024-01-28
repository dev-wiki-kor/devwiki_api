package com.dk0124.project.user.adapter.out.user;

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


/**
 * 회원 가입 시 사용자 정보를 저장하는 어댑터입니다.
 * 사용자 고유 ID 및 닉네임의 가용성을 확인하고 사용자를 등록합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GithubJoinAdapter implements GithubUserJoinPort {

    private final UserGithubInfoEntityRepository userGithubInfoEntityRepository;

    private final UserProfileEntityRepository userProfileEntityRepository;

    private final UserMetaEntityRepository userMetaEntityRepository;


    /**
     * Github Unique ID가 사용 가능한지 확인합니다.
     *
     * @param uniqueId 고유 Github Unique ID 입니다.
     * @return 사용 가능하면 true, 그렇지 않으면 false를 반환합니다.
     */
    @Override
    public boolean isUniqueIdAvailable(String uniqueId) {
        return !userGithubInfoEntityRepository.existsByGithubUniqueId(uniqueId);
    }

    /**
     * 닉네임이 사용 가능한지 확인합니다.
     *
     * @param nickname 닉네임입니다.
     * @return 사용 가능하면 true, 그렇지 않으면 false를 반환합니다.
     */
    @Override
    public boolean isNickNameAvailable(String nickname) {
        return !userProfileEntityRepository.existsByNickname(nickname);
    }


    /**
     * 사용자 정보를 등록합니다.
     *
     * @param joinCommand 사용자 정보를 담은 JoinCommand 객체입니다.
     */
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
