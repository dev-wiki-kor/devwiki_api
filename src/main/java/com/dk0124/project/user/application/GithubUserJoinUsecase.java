package com.dk0124.project.user.application;

import com.dk0124.project.user.adapter.in.dto.GithubUserJoinRequest;

public interface GithubUserJoinUsecase {
    public void join(GithubUserJoinRequest githubUserJoinRequest);
}
