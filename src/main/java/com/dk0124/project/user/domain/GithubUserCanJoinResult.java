package com.dk0124.project.user.domain;

public record GithubUserCanJoinResult(
        boolean success,
        String bearerToken
) {
}
