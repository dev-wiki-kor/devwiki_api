package com.dk0124.project.common.user.join;

import lombok.NonNull;

public record GithubUserJoinCommand(
        @NonNull String code,
        @NonNull String nickname
) {
}
