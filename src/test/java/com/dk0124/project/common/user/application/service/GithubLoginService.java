package com.dk0124.project.common.user.application.service;

import com.dk0124.project.common.user.CreateLoginSession;
import com.dk0124.project.common.user.GithubUserInfo;
import com.dk0124.project.common.user.adapter.out.github.GitHubClientUserInfo;
import com.dk0124.project.common.user.adapter.out.github.GithubClientAccessToken;
import com.dk0124.project.common.user.application.GithubLoginRequest;
import com.dk0124.project.common.user.application.GithubLoginUsecase;
import com.dk0124.project.common.user.application.port.out.LoginHistoryPort;
import com.dk0124.project.common.user.application.port.out.UserExistCheckPort;
import com.dk0124.project.common.user.exception.GithubAuthFailException;
import com.dk0124.project.common.user.exception.UserNotExistException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubLoginService implements GithubLoginUsecase {
    private final GithubClientAccessToken githubClientAccessToken;
    private final GitHubClientUserInfo gitHubClientUserInfo;
    private final UserExistCheckPort userExistCheckPort;
    private final LoginHistoryPort loginHistoryPort;
    private final CreateLoginSession createLoginSession;

    @Override
    public void login(GithubLoginRequest loginRequest) {
        // get access token & user info
        var githubUserInfo = callGithubUserInfo(loginRequest);

        // get user info from db by unique info
        var loginUser = userExistCheckPort.findByGithubUniqueId(githubUserInfo.uniqueId())
                .orElseThrow(() -> new UserNotExistException());

        // update history
        loginHistoryPort.writeLoginHistory(loginUser.getId());

        // set session
        createLoginSession.create(loginUser);
    }

    private GithubUserInfo callGithubUserInfo(GithubLoginRequest loginRequest) {
        try {
            // get access token
            var githubAccessTokenResponse
                    = githubClientAccessToken.call(loginRequest.cookie(), "CLIENT_ID", "CLIENT_SECRET", loginRequest.code());
            // get user info
            return gitHubClientUserInfo.call(githubAccessTokenResponse.getBearerToken());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GithubAuthFailException();
        }
    }
}
