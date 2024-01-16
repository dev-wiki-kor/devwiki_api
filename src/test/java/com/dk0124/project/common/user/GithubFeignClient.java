package com.dk0124.project.common.user;

public interface GithubFeignClient {

    GithubAccessTokenResponse requireAccessToken (String cookie, String GITHUB_CLIENT_ID, String GITHUB_SECRET, String code);

    GithubUserInfo requireUserInfo (String accessToken);

}
