package com.dk0124.project.user.adapter.out.github;

import com.dk0124.project.global.exception.GithubApiException;
import com.dk0124.project.user.application.port.out.GithubApiPort;
import com.dk0124.project.user.exception.GithubAuthFailException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 이 컴포넌트는 GitHub API 호출을 위한 어댑터 역할을 합니다.
 * GitHub 액세스 토큰 및 사용자 정보를 가져오기 위한 메서드를 제공합니다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GithubApiAdapter implements GithubApiPort {

    @Value("${app.var.github-client-id}")
    private String GITHUB_CLIENT_ID;

    @Value("${app.var.github-client-secret}")
    private String GITHUB_CLIENT_SECRET;


    private final GithubClientAccessToken githubClientAccessToken;
    private final GitHubClientUserInfo gitHubClientUserInfo;


    /**
     * 제공된 인증 코드를 사용하여 GitHub 액세스 토큰을 가져옵니다.
     *
     * @param code GitHub OAuth에서 받은 인증 코드입니다.
     * @return GitHub 액세스 토큰을 포함한 응답을 반환합니다.
     */
    @Override
    public GithubAccessTokenResponse callAccessToken(String code) {
        try {
            return githubClientAccessToken.call(
                    GITHUB_CLIENT_ID,
                    GITHUB_CLIENT_SECRET, code);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GithubApiException();
        }
    }

    /**
     * 제공된 베어러 토큰을 사용하여 GitHub 사용자 정보를 가져옵니다.
     *
     * @param bearerToken 인증에 사용되는 베어러 토큰입니다.
     * @return URL, 이메일 및 고유 ID를 포함한 GitHub 사용자 정보입니다.
     */
    @Override
    public GitHubClientUserInfoResponse callUserInfo(String bearerToken) {
        try {
            return gitHubClientUserInfo.call(bearerToken);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new GithubApiException();
        }
    }

    /**
     * 제공된 인증 코드를 사용하여 GitHub 사용자 정보를 가져옵니다.
     * 이 메서드는 먼저 액세스 토큰을 얻은 다음 사용자 정보를 가져옵니다.
     *
     * @param code GitHub OAuth에서 받은 인증 코드입니다.
     * @return URL, 이메일 및 고유 ID를 포함한 GitHub 사용자 정보입니다.
     */
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
            throw new GithubApiException();
        }
    }
}
