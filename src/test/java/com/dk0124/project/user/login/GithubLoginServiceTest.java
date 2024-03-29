package com.dk0124.project.user.login;

import com.dk0124.project.user.adapter.out.github.GithubAccessTokenResponse;
import com.dk0124.project.user.adapter.out.github.GitHubClientUserInfoResponse;
import com.dk0124.project.user.application.port.out.GithubApiPort;
import com.dk0124.project.user.application.service.LoginSuccessHandler;
import com.dk0124.project.user.domain.UserGithubInfo;
import com.dk0124.project.user.adapter.in.dto.GithubLoginRequest;
import com.dk0124.project.user.application.port.out.LoginHistoryPort;
import com.dk0124.project.user.application.port.out.UserExistCheckPort;

import com.dk0124.project.user.application.service.GithubLoginService;
import com.dk0124.project.user.domain.UserRole;
import com.dk0124.project.user.domain.UserStatus;
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
    GithubApiPort githubApiPort;

    @Mock
    UserExistCheckPort userExistCheckPort;

    @Mock
    LoginHistoryPort loginHistoryPort;

    @Mock
    LoginSuccessHandler loginSuccessHandler;

    @InjectMocks
    GithubLoginService githubLoginService;

    private final String RETURN_CODE_FOR_TEST = "0";
    private final String GIHUB_AUTH_RES_COOKIE_FOR_TEST = "0";

    @Test
    void 로그인_정상_실행() {

        // Given
        var githubLoginRequest = new GithubLoginRequest(
                RETURN_CODE_FOR_TEST

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
                Set.of(UserStatus.NORMAL),
                true
        );


        when(githubApiPort.callGithubUserInfoByCode(any(String.class)))
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
                RETURN_CODE_FOR_TEST
        );

        when(githubApiPort.callGithubUserInfoByCode(any(String.class)))
                .thenThrow(new GithubAuthFailException("테스트 익샙션"));


        assertThrows(GithubAuthFailException.class, () -> githubLoginService.login(githubLoginRequest));
    }

    @Test
    void 로그인_실패_github_인증실패_깃허브_유저정보_없음() {

        // Given
        var githubLoginRequest = new GithubLoginRequest(
                RETURN_CODE_FOR_TEST
        );

        var githubAccessTokenResponse = new GithubAccessTokenResponse(
                "accessToken", "tokenType", "scope"
        );

        when(githubApiPort.callGithubUserInfoByCode(any(String.class)))
                .thenThrow(new GithubAuthFailException("테스트 익샙션"));

        assertThrows(GithubAuthFailException.class, () -> githubLoginService.login(githubLoginRequest));

    }

    @Test
    void 로그인_실패_유저정보_불러오기_실패() {
        // Given
        var githubLoginRequest = new GithubLoginRequest(
                RETURN_CODE_FOR_TEST
        );

        var githubAccessTokenResponse = new GithubAccessTokenResponse(
                "accessToken", "tokenType", "scope"
        );

        var githubUserInfo = new GitHubClientUserInfoResponse("email", "uniqueId", "nickname", "profile", "pageUrl");


        when(githubApiPort.callGithubUserInfoByCode(any(String.class)))
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
