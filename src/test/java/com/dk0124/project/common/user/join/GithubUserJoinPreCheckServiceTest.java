package com.dk0124.project.common.user.join;

import com.dk0124.project.user.adapter.out.github.GithubAccessTokenResponse;
import com.dk0124.project.user.application.port.out.GithubUserJoinPort;
import com.dk0124.project.user.application.service.GithubUserJoinPreCheckService;
import com.dk0124.project.user.adapter.out.github.GitHubClientUserInfoResponse;
import com.dk0124.project.user.application.port.out.GithubApiPort;
import com.dk0124.project.user.application.port.out.UserExistCheckPort;
import com.dk0124.project.user.domain.UserGithubInfo;
import com.dk0124.project.user.domain.UserStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class GithubUserJoinPreCheckServiceTest {
    @Mock
    private GithubApiPort githubApiPort;


    @Mock
    private  GithubUserJoinPort githubUserJoinPort;

    @InjectMocks
    private GithubUserJoinPreCheckService githubUserJoinPreCheckService;

    @Test
    void canRegister_성공() {
        var code = "code123";
        var githubAccessTokenResponse = new GithubAccessTokenResponse("123", "tokenType", "scope");
        when(githubApiPort.callAccessToken(code)).thenReturn(githubAccessTokenResponse);

        when(githubApiPort.callUserInfo(githubAccessTokenResponse.getBearerToken()))
                .thenReturn(new GitHubClientUserInfoResponse("", "uniqueId123", "nickname", "url", "url"));


        when(githubUserJoinPort.isUniqueIdAvailable("uniqueId123")).thenReturn(true);

        assertTrue(githubUserJoinPreCheckService.canRegister(code).success());

    }

    @Test
    void canRegister_실패_이미_존재하는_유저() {
        String code = "code123";
        var githubAccessTokenResponse = new GithubAccessTokenResponse("123", "tokenType", "scope");
        when(githubApiPort.callAccessToken(code)).thenReturn(githubAccessTokenResponse);

        when(githubApiPort.callUserInfo(githubAccessTokenResponse.getBearerToken()))
                .thenReturn(new GitHubClientUserInfoResponse("", "uniqueId123", "nickname", "url", "url"));

        when(githubUserJoinPort.isUniqueIdAvailable("uniqueId123")).thenReturn(false);

        assertFalse(githubUserJoinPreCheckService.canRegister(code).success());
    }
}
