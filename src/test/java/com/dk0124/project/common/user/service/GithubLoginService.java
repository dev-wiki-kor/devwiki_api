package com.dk0124.project.common.user.service;

import com.dk0124.project.common.user.GithubFeignClient;
import com.dk0124.project.common.user.application.GithubLoginUsecase;
import com.dk0124.project.common.user.application.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubLoginService implements GithubLoginUsecase {

    private final GithubFeignClient githubFeignClient;

    @Override
    public void login(LoginRequest loginRequest) {

        // get access token

        // get user info

        // update history

        // set session


    }
}
