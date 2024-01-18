package com.dk0124.project.common.user;

import com.dk0124.project.auth.domain.UserRole;
import com.dk0124.project.common.user.adapter.out.github.GitHubClientUserInfo;
import com.dk0124.project.common.user.adapter.out.github.GithubClientAccessToken;
import com.dk0124.project.common.user.adapter.out.user.UserGithubInfo;
import com.dk0124.project.common.user.application.GithubLoginRequest;
import com.dk0124.project.common.user.application.port.out.LoginHistoryPort;
import com.dk0124.project.common.user.application.port.out.UserExistCheckPort;

import com.dk0124.project.common.user.application.service.GithubLoginService;
import com.dk0124.project.common.user.exception.GithubAuthFailException;
import com.dk0124.project.common.user.exception.UserNotExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

    // TODO : 아직 세션 설계 없음.
    //@Mock
    //CreateLoginSession createLoginSession;

    @InjectMocks
    GithubLoginService githubLoginService;

    private final String RETURN_CODE_FOR_TEST = "0";
    private final String GIHUB_AUTH_RES_COOKIE_FOR_TEST = "0";

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


        var githubUserInfo = new GithubUserInfo("email", "uniqueId", "nickname", "profile", "pageUrl");

        var userGithubInfo = UserGithubInfo.of(
                1L,
                1L,
                githubUserInfo.uniqueId(),
                Set.of(UserRole.USER),
                true
        );

        var loginSession = new LoginSession(
                null, userGithubInfo.getUserMetaId(), List.of(Role.USER), LocalDateTime.now(), LocalDateTime.now()
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
                .thenReturn(userGithubInfo);

        doNothing().when(loginHistoryPort).writeLoginHistory(userGithubInfo.getUserMetaId());

        /*
        // 아직 session 설계 없음 .
        when(createLoginSession.create(any())).thenReturn(loginSession);
        */

        assertDoesNotThrow(() -> githubLoginService.login(githubLoginRequest));
    }

    @Test
    void 로그인_실패_github_인증실패_access_token_발급없음() {

        // Given
        var githubLoginRequest = new GithubLoginRequest(
                RETURN_CODE_FOR_TEST,
                GIHUB_AUTH_RES_COOKIE_FOR_TEST
        );

        when(githubClientAccessToken.call(
                eq(githubLoginRequest.cookie()),
                any(String.class),
                any(String.class),
                eq(githubLoginRequest.code())
        )).thenThrow(new GithubAuthFailException());

        assertThrows(GithubAuthFailException.class, () -> githubLoginService.login(githubLoginRequest));
    }

    @Test
    void 로그인_실패_github_인증실패_깃허브_유저정보_없음() {

        // Given
        var githubLoginRequest = new GithubLoginRequest(
                RETURN_CODE_FOR_TEST,
                GIHUB_AUTH_RES_COOKIE_FOR_TEST
        );

        var githubAccessTokenResponse = new GithubAccessTokenResponse(
                "accessToken", "tokenType", "scope"
        );

        when(githubClientAccessToken.call(
                eq(githubLoginRequest.cookie()),
                any(String.class),
                any(String.class),
                eq(githubLoginRequest.code())
        )).thenReturn(githubAccessTokenResponse);

        when(githubClientUserInfo.call(githubAccessTokenResponse.getBearerToken()))
                .thenThrow(new GithubAuthFailException());

        assertThrows(GithubAuthFailException.class, () -> githubLoginService.login(githubLoginRequest));

    }

    @Test
    void 로그인_실패_유저정보_불러오기_실패() {
        // Given
        var githubLoginRequest = new GithubLoginRequest(
                RETURN_CODE_FOR_TEST,
                GIHUB_AUTH_RES_COOKIE_FOR_TEST
        );

        var githubAccessTokenResponse = new GithubAccessTokenResponse(
                "accessToken", "tokenType", "scope"
        );

        var githubUserInfo = new GithubUserInfo("email", "uniqueId", "nickname", "profile", "pageUrl");


        when(githubClientAccessToken.call(
                eq(githubLoginRequest.cookie()),
                any(String.class),
                any(String.class),
                eq(githubLoginRequest.code())
        )).thenReturn(githubAccessTokenResponse);

        when(githubClientUserInfo.call(githubAccessTokenResponse.getBearerToken()))
                .thenReturn(githubUserInfo);


        when(userExistCheckPort.findByGithubUniqueId(githubUserInfo.uniqueId()))
                .thenThrow(UserNotExistException.class);

        assertThrows(UserNotExistException.class, () -> githubLoginService.login(githubLoginRequest));
    }

    /* TODO : 세션 관련 설계 필요, 별도 디렉토리에서 수행   */
    /*
    @Test
    void 로그인_실패_세션생성_실패() {
    }
     */
}
