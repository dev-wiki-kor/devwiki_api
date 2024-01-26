package com.dk0124.project.common.user.join;

public interface GithubUserJoinPreCheckUsecase {
    boolean canRegister(String code);
}
