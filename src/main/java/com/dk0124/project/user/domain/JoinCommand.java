package com.dk0124.project.user.domain;

public record JoinCommand(
        String githubUniqueId,
        String email,
        String pageUrl,
        String profileUrl,
        String nickname
) {
}
