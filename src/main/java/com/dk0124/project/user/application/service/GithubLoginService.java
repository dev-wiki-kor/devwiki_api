package com.dk0124.project.user.application.service;


import com.dk0124.project.config.security.csrf.CsrfTokenRegister;
import com.dk0124.project.config.security.session.LoginSessionRegister;
import com.dk0124.project.user.adapter.in.dto.GithubLoginRequest;
import com.dk0124.project.user.application.GithubLoginUsecase;
import com.dk0124.project.user.application.port.out.GithubApiPort;
import com.dk0124.project.user.application.port.out.LoginHistoryPort;
import com.dk0124.project.user.application.port.out.UserExistCheckPort;

import com.dk0124.project.user.domain.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;

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
