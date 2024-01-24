package com.dk0124.project.common.session;

import com.dk0124.project.user.domain.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
@Slf4j
class LoginSessionServiceImplTest {
/*
    @Autowired
    private LoginSessionService loginSessionService;


    @Test
    void 레디스_세션_객체_저장후_조회() {
        var session = loginSessionService.createLoginSession(0L, Set.of(UserRole.USER, UserRole.ADMIN));
        loginSessionService.saveLoginSession(session);

        var retrieved = loginSessionService.getLoginSession(session.getUuid());
        assertNotNull(retrieved);
    }

    @Test
    void 레디스_세션_갱신_후_조회() {
        // given
        var session = userDoLogin();
        var beforeRenew = userRequestWithSessionKey(session);

        // when
        var afterRenew = loginSessionService.renewLoginSession(beforeRenew);
        loginSessionService.deleteLoginSession(beforeRenew.getUuid());
        loginSessionService.saveLoginSession(afterRenew);

        //then
        var resultSession = loginSessionService.getLoginSession(afterRenew.getUuid());
        assertThat(afterRenew.getUuid()).isEqualTo(resultSession.getUuid());
        assertThat(afterRenew.getSessionCreated()).isEqualTo(resultSession.getSessionCreated());
        assertThat(afterRenew.getSessionExpired()).isEqualTo(resultSession.getSessionExpired());
    }

    private LoginSession userRequestWithSessionKey(LoginSession session) {
        return loginSessionService.getLoginSession(session.getUuid());
    }

    private LoginSession userDoLogin() {
        var session = loginSessionService.createLoginSession(0L, Set.of(UserRole.USER, UserRole.ADMIN));
        loginSessionService.saveLoginSession(session);
        return session;
    }

 */


}