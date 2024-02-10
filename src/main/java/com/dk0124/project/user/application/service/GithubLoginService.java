package com.dk0124.project.user.application.service;



import com.dk0124.project.user.adapter.in.dto.GithubLoginRequest;
import com.dk0124.project.user.application.port.in.GithubLoginUsecase;
import com.dk0124.project.user.application.port.out.GithubApiPort;
import com.dk0124.project.user.application.port.out.LoginHistoryPort;
import com.dk0124.project.user.application.port.out.UserExistCheckPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * 로그인 서비스 클래스
 *
 * 1) Github API를 호출하여 unique id와 사용자 정보를 획득합니다.
 * 2) 데이터베이스에서 unique id 를 값으로 가지는 로그인 가능한 유저 정보가 있는지 확인 .
 * 3) 사용자 로그인 히스토리를 생성합니다.
 * 4) 로그인 성공 시 세션을 생성하고 CSRF 토큰을 생성합니다.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubLoginService implements GithubLoginUsecase {

    private final UserExistCheckPort userExistCheckPort;
    private final LoginHistoryPort loginHistoryPort;
    private final GithubApiPort githubApiPort;

    private final LoginSuccessHandler loginSuccessHandler;


    @Override
    public void login(GithubLoginRequest loginRequest) {
        // 1) get access token & user info from github
        var gitHubClientUserInfoResponse =
                githubApiPort.callGithubUserInfoByCode(loginRequest.code());

        //  2) get user info from db by unique info
        var loginUser = userExistCheckPort.findByGithubUniqueId(gitHubClientUserInfoResponse.uniqueId());

        // 3) update history
        loginHistoryPort.writeLoginHistory(loginUser.getUserMetaId());

        // 4) 로그인 성공 시 세션 생성, csrf 토큰 생성.
        loginSuccessHandler.createSession(loginUser.getUserMetaId(), loginUser.getUserRoles());
    }
}
