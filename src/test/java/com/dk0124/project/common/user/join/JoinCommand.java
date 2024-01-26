package com.dk0124.project.common.user.join;

public record JoinCommand(
        String githubUniqueId,
        String email,
        String pageUrl,
        String profileUrl,
        String nickname
) {
}
