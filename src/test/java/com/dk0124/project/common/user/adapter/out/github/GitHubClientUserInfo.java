package com.dk0124.project.common.user.adapter.out.github;

import com.dk0124.project.common.user.GithubUserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "gitHubClientUserInfoFeign", url = "https://api.github.com", configuration = {GithubRequestHeader.class})
public interface GitHubClientUserInfo {
    @GetMapping("/user")
    GithubUserInfo call(@RequestHeader("Authorization") String accessToken);
}

