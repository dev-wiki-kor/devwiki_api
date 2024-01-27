package com.dk0124.project.user.domain;

import lombok.NonNull;

public record GithubUserJoinCommand(
        @NonNull String code,
        @NonNull String nickname
) {
}
