package com.dk0124.project.user.application.port.out;


import com.dk0124.project.user.adapter.out.github.GitHubClientUserInfoResponse;
import com.dk0124.project.user.adapter.out.github.GithubAccessTokenResponse;

public interface GithubApiPort {
    GithubAccessTokenResponse callAccessToken(String code);

    GitHubClientUserInfoResponse callUserInfo(String bearerToken);

    GitHubClientUserInfoResponse callGithubUserInfoByCode(String code);
}
