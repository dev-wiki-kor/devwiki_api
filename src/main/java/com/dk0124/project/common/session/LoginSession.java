package com.dk0124.project.common.session;

import com.dk0124.project.user.domain.UserRole;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Getter
@RedisHash(value = "login_session", timeToLive = 1000)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginSession {

    @Id
    private UUID uuid;

    private final Long userId;
    private final Set<UserRole> userRoles;

    private final LocalDateTime sessionCreated;
    private final LocalDateTime sessionExpired;

    public static LoginSession of(UUID uuid, Long userId, Set<UserRole> userRoles, LocalDateTime sessionCreated, LocalDateTime sessionExpired) {
        return new LoginSession(uuid, userId, userRoles, sessionCreated, sessionExpired);
    }

    public LoginSession withNewUuid(UUID uuid) {
        return new LoginSession(uuid, this.userId, this.userRoles, this.sessionCreated, this.sessionExpired);
    }
}
