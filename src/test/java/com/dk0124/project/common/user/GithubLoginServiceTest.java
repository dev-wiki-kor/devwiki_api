package com.dk0124.project.common.user;

import com.dk0124.project.user.adapter.out.github.GithubAccessTokenResponse;
import com.dk0124.project.user.adapter.out.github.GitHubClientUserInfo;
import com.dk0124.project.user.adapter.out.github.GitHubClientUserInfoResponse;
import com.dk0124.project.user.adapter.out.github.GithubClientAccessToken;
import com.dk0124.project.user.domain.UserGithubInfo;
import com.dk0124.project.user.application.GithubLoginRequest;
import com.dk0124.project.user.application.port.out.LoginHistoryPort;
import com.dk0124.project.user.application.port.out.UserExistCheckPort;

import com.dk0124.project.user.application.service.GithubLoginService;
import com.dk0124.project.user.domain.UserRole;
import com.dk0124.project.user.exception.GithubAuthFailException;
import com.dk0124.project.user.exception.UserNotExistException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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


        var githubUserInfo = new GitHubClientUserInfoResponse("email", "uniqueId", "nickname", "profile", "pageUrl");

        var userGithubInfo = UserGithubInfo.of(
                1L,
                1L,
                githubUserInfo.uniqueId(),
                Set.of(UserRole.USER),
                true
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

        var githubUserInfo = new GitHubClientUserInfoResponse("email", "uniqueId", "nickname", "profile", "pageUrl");


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
