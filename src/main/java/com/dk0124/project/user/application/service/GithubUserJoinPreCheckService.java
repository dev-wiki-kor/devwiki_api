package com.dk0124.project.user.application.service;

import com.dk0124.project.user.domain.GithubUserCanJoinResult;
import com.dk0124.project.user.application.port.out.GithubApiPort;
import com.dk0124.project.user.application.GithubUserJoinPreCheckUsecase;
import com.dk0124.project.user.application.port.out.GithubUserJoinPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubUserJoinPreCheckService implements GithubUserJoinPreCheckUsecase {

    private final GithubApiPort githubApiPort;

    private final GithubUserJoinPort githubUserJoinPort;

    @Override
    public GithubUserCanJoinResult canRegister(String code) {
        var bearerToken = githubApiPort.callAccessToken(code).getBearerToken();
        var githubUserInfo = githubApiPort.callUserInfo(bearerToken);

        var canJoin = githubUserJoinPort.isUniqueIdAvailable(githubUserInfo.uniqueId());
        return new GithubUserCanJoinResult(canJoin, bearerToken);
    }
}
