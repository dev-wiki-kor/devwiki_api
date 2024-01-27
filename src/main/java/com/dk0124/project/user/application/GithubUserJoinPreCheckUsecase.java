package com.dk0124.project.user.application;

import com.dk0124.project.user.domain.GithubUserCanJoinResult;

public interface GithubUserJoinPreCheckUsecase {
    GithubUserCanJoinResult canRegister(String code);
}
