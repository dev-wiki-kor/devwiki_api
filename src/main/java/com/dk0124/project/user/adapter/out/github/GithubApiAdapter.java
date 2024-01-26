package com.dk0124.project.user.adapter.out.github;

import com.dk0124.project.user.application.port.out.GithubApiPort;
import com.dk0124.project.user.exception.GithubAuthFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GithubApiAdapter implements GithubApiPort {

    @Value("${app.var.github-client-id}")
    private final String GITHUB_CLIENT_ID;

    @Value("${app.var.github-client-secret}")
    private final String GITHUB_CLIENT_SECRET;


    private final GithubClientAccessToken githubClientAccessToken;
    private final GitHubClientUserInfo gitHubClientUserInfo;


    @Override
    public GithubAccessTokenResponse callAccessToken(String code) {
        try {
            return githubClientAccessToken.call(
                    GITHUB_CLIENT_ID,
                    GITHUB_CLIENT_SECRET, code);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GithubAuthFailException();
        }
    }

    @Override
    public GitHubClientUserInfoResponse callUserInfo(String bearerToken) {
        try {
            return gitHubClientUserInfo.call(bearerToken);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GithubAuthFailException();
        }
    }


    @Override
    public GitHubClientUserInfoResponse callGithubUserInfoByCode(String code) {
        try {
            // get access token
            var githubAccessTokenResponse
                    = githubClientAccessToken.call(
                        GITHUB_CLIENT_ID,
                        GITHUB_CLIENT_SECRET, code);
            // get user info
            return gitHubClientUserInfo.call(githubAccessTokenResponse.getBearerToken());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GithubAuthFailException();
        }
    }
}
