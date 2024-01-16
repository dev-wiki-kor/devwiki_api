package com.dk0124.project.common.user;

import com.dk0124.project.common.user.adapter.out.github.GitHubClientUserInfo;
import com.dk0124.project.common.user.adapter.out.github.GithubClientAccessToken;
import com.dk0124.project.common.user.adapter.out.user.User;
import com.dk0124.project.common.user.application.GithubLoginRequest;
import com.dk0124.project.common.user.application.port.out.LoginHistoryPort;
import com.dk0124.project.common.user.application.port.out.UserExistCheckPort;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GithubLoginServiceTest {

    @Mock
    GithubClientAccessToken githubClientAccessToken;
    @Mock
    GitHubClientUserInfo githubClientUserInfo;
    @Mock
    UserExistCheckPort userExistCheckPort;
    @Mock
    LoginHistoryPort loginHistoryPort;
    @Mock
    CreateLoginSession createLoginSession;

    @InjectMocks
    GithubLoginService githubLoginService;

    private final String RETURN_CODE_FOR_TEST = "0";
    private final String GIHUB_AUTH_RES_COOKIE_FOR_TEST = "0";
    private final String GITHUB_CLIENT_ID_FOR_TEST = "HELLO";
    private final String GITHUB_SECRET_FOR_TEST = "WORLD";

    @Test
    void 로그인_정상_실행() {

        // Given
        var githubLoginRequest = new GithubLoginRequest(
                RETURN_CODE_FOR_TEST,
                GIHUB_AUTH_RES_COOKIE_FOR_TEST
        );

        var githubAccessTokenResponse = new GithubAccessTokenResponse(
                "accessToken", "tokenType", "scope"
        );

        var loginUser = new User(1L);

        var githubUserInfo = new GithubUserInfo("email", "uniqueId", "nickname", "profile", "pageUrl");

        var loginSession = new LoginSession(
                null, loginUser.getId(), List.of(Role.USER), LocalDateTime.now(), LocalDateTime.now()
        );

        when(githubClientAccessToken.call(
                eq(githubLoginRequest.cookie()),
                any(String.class),
                any(String.class),
                eq(githubLoginRequest.code())
        )).thenReturn(githubAccessTokenResponse);

        when(githubClientUserInfo.call(githubAccessTokenResponse.getBearerToken()))
                .thenReturn(githubUserInfo);


        when(userExistCheckPort.findByGithubUniqueId(githubUserInfo.uniqueId()))
                .thenReturn(Optional.of(loginUser));

        doNothing().when(loginHistoryPort).writeLoginHistory(loginUser.getId());

        when(createLoginSession.create(loginUser)).thenReturn(loginSession);

        githubLoginService.login(githubLoginRequest);
    }

    @Test
    void 로그인_실패_github_인증실패() {

    }

    @Test
    void 로그인_실패_유저정보_불러오기_실패() {

    }

    @Test
    void 로그인_실패_세션생성_실패() {

    }
}
