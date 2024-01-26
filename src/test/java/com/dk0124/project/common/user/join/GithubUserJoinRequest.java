package com.dk0124.project.common.user.join;

import lombok.NonNull;

public record GithubUserJoinRequest(
        @NonNull String code,
        @NonNull String nickname
) {
}
