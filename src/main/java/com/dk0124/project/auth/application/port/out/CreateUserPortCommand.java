package com.dk0124.project.auth.application.port.out;

import com.dk0124.project.auth.domain.UserRole;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record CreateUserPortCommand(
        @NotNull String userName, @NotNull String password, @NotNull Set<UserRole> userRole) {
}
