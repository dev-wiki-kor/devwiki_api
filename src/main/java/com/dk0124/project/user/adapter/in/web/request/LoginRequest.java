package com.dk0124.project.user.adapter.in.web.request;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(@NotNull String userName, @NotNull String password) {
}
