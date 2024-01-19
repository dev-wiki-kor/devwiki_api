package com.dk0124.project.user.adapter.out.github;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GitHubClientUserInfoResponse(
        String email,

        @JsonProperty("id")
        String uniqueId,

        @JsonProperty("login")
        String nickname,

        @JsonProperty("avatar_url")
        String profileUrl,

        @JsonProperty("html_url")
        String pageUrl
) {
}
