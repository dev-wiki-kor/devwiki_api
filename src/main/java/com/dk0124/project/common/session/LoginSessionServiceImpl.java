package com.dk0124.project.common.session;

import com.dk0124.project.auth.domain.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoginSessionServiceImpl implements LoginSessionService {
    private final UUIDHolder uuidHolder;
    private final LoginSessionRepository loginSessionRepository;

    @Value("${app.var.session-active}")
    private Long SESSION_ACTIVE_SECOND;

    @Override
    public LoginSession createLoginSession(Long userId, Set<UserRole> userRole) {
        var key = uuidHolder.random();
        var sessionCreated = LocalDateTime.now();
        var sessionExpired = LocalDateTime.now().plusSeconds(SESSION_ACTIVE_SECOND);
        return LoginSession.of(key, userId, userRole, sessionCreated, sessionExpired);
    }

    @Override
    public LoginSession getLoginSession(UUID sessionKey) {
        return loginSessionRepository.findById(sessionKey)
                .orElseThrow(() -> new RuntimeException("세션 없음 "));
    }

    @Override
    public void saveLoginSession(LoginSession session) {
        loginSessionRepository.save(session);
    }

    @Override
    public LoginSession renewLoginSession(LoginSession loginSession) {
        return createLoginSession(loginSession.getUserId(), loginSession.getUserRoles());
    }

    @Override
    public void deleteLoginSession(UUID uuid) {
        loginSessionRepository.deleteById(uuid);
    }


}
