package com.dk0124.project.user.adapter.out.user;

import com.dk0124.project.user.domain.UserStatus;
import com.dk0124.project.user.domain.UserGithubInfo;
import com.dk0124.project.user.adapter.out.user.repository.UserGithubInfoEntityRepository;
import com.dk0124.project.user.adapter.out.user.repository.UserMetaEntityRepository;

import com.dk0124.project.user.application.port.out.UserExistCheckPort;
import com.dk0124.project.user.exception.BannedUserLoginExceptiopn;
import com.dk0124.project.user.exception.UserNotExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * GitHub OAuth 코드에 해당하는 사용자 정보를 데이터베이스에서 조회하는 어댑터입니다.
 * 다음과 같은 과정을 거쳐 사용자 정보를 조회합니다:
 * 1) GitHub 정보에서 고유 ID에 해당하는 사용자를 조회합니다.
 * 2) 사용자가 금지 상태인 경우 반환하지 않습니다.
 * 3) 위의 정보를 사용하여 UserGithubInfo로 집계하여 반환합니다.
 */
@Service
@RequiredArgsConstructor
public class UserExistCheckAdapter implements UserExistCheckPort {

    private final UserGithubInfoEntityRepository userGithubInfoEntityRepository;

    private final UserMetaEntityRepository userMetaEntityRepository;

    @Override
    public UserGithubInfo findByGithubUniqueId(String githubUniqueId) {

        // 1) github info 의 github unique id 에 해당하는 유저를 쿼리
        var userGithubInfo = userGithubInfoEntityRepository.findByGithubUniqueId(githubUniqueId)
                .orElseThrow(()-> new UserNotExistException());
        // 2) 정보가 있다면 meta user info 정보를 쿼리
        var userInfo = userMetaEntityRepository.findByIdAndActive(userGithubInfo.getUserMetaId(), true)
                .orElseThrow(()-> new UserNotExistException());
        //3 ) user 가 banned 상태라면 리턴하지 않음
        if(userInfo.getUserStatus().contains(UserStatus.BANNED))
            throw new BannedUserLoginExceptiopn("로그인 할 수 없는 유저입니다. ( 제한된 유저 )");

        // 4) 위의 유저를 UserGithubInfo 로 집계  해서 리턴.
        return UserGithubInfo.of(
                userGithubInfo.getId(),
                userGithubInfo.getUserMetaId(),
                userGithubInfo.getGithubUniqueId(),
                userInfo.getUserRoles(),
                userInfo.getUserStatus(),
                userInfo.isActive()
        );
    }
}
