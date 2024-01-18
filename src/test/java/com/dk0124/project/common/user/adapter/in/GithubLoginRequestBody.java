package com.dk0124.project.common.user.adapter.in;

import lombok.NonNull;

public record GithubLoginRequestBody(@NonNull String code) {
}
