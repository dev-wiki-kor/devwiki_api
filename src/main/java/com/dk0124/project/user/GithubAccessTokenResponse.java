package com.dk0124.project.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GithubAccessTokenResponse(
        @JsonProperty("access_token") String accessToken,
        @JsonProperty("token_type") String tokenType,
        @JsonProperty("scope") String scope) {

    public String getBearerToken() {
        return "Bearer " + accessToken;
    }

}
