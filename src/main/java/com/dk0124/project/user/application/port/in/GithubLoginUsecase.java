package com.dk0124.project.user.application.port.in;

import com.dk0124.project.user.adapter.in.dto.GithubLoginRequest;

public interface GithubLoginUsecase {
    void login(GithubLoginRequest loginRequest);
}
