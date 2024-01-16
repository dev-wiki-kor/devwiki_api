package com.dk0124.project.common.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record LoginSession(
        UUID uuid,
        Long userId,
        List<Role> userRoles,
        LocalDateTime expiredAt,
        LocalDateTime createdAt
) {
}
