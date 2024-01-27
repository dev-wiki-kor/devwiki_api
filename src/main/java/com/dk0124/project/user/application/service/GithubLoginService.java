package com.dk0124.project.user.application.service;


import com.dk0124.project.user.adapter.in.dto.GithubLoginRequest;
import com.dk0124.project.user.application.GithubLoginUsecase;
import com.dk0124.project.user.application.port.out.GithubApiPort;
import com.dk0124.project.user.application.port.out.LoginHistoryPort;
import com.dk0124.project.user.application.port.out.UserExistCheckPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubLoginService implements GithubLoginUsecase {

    private final UserExistCheckPort userExistCheckPort;
    private final LoginHistoryPort loginHistoryPort;
    private final GithubApiPort githubApiPort;


    @Override
    public void login(GithubLoginRequest loginRequest) {
        // get access token & user info from github
        var gitHubClientUserInfoResponse =
                githubApiPort.callGithubUserInfoByCode(loginRequest.code());

        // get user info from db by unique info
        var loginUser = userExistCheckPort.findByGithubUniqueId(gitHubClientUserInfoResponse.uniqueId());

        // update history
        loginHistoryPort.writeLoginHistory(loginUser.getUserMetaId());

/*
  // TODO : 세션 등록 설계 필요 .

        createLoginSession.create(loginUser);
 */
    }

}
