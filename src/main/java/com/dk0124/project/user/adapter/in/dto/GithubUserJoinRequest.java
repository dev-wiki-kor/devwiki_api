package com.dk0124.project.user.adapter.in.dto;

import lombok.NonNull;

public record GithubUserJoinRequest(
        @NonNull String code,
        @NonNull String nickname,
        @NonNull String bearerToken
) {
}
