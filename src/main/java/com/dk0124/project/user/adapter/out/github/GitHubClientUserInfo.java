package com.dk0124.project.user.adapter.out.github;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "gitHubClientUserInfoFeign", url = "https://api.github.com", configuration = {GithubRequestHeader.class})
public interface GitHubClientUserInfo {
    @GetMapping("/user")
    GitHubClientUserInfoResponse call(@RequestHeader("Authorization") String accessToken);
}

