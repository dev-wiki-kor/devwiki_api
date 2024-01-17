package com.dk0124.project.common.user.adapter.out.github;

import com.dk0124.project.common.user.GithubAccessTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "githubClientAccessToken", url = "https://github.com", configuration = {GithubRequestHeader.class})
public interface GithubClientAccessToken {
    @PostMapping(value = "login/oauth/access_token", produces = MediaType.APPLICATION_JSON_VALUE)
    GithubAccessTokenResponse call (
            @RequestHeader("Cookie") String cookie,
            @RequestParam("client_id") String clientId,
            @RequestParam("client_secret") String clientSecret,
            @RequestParam("code") String code
    );
}
