package com.dk0124.project.common.session;


import com.dk0124.project.auth.domain.UserRole;
import org.springframework.data.util.Pair;

import java.util.Set;
import java.util.UUID;

public interface LoginSessionService {
    public LoginSession createLoginSession(Long userId, Set<UserRole> userRole);

    public LoginSession getLoginSession(UUID sessionKey);

    public void saveLoginSession(LoginSession session);

    public LoginSession renewLoginSession(LoginSession loginSession);

    public void deleteLoginSession(UUID uuid);
}
