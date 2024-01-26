package com.dk0124.project.common.user.join.test;

import com.dk0124.project.common.user.join.*;
import com.dk0124.project.user.adapter.out.github.GitHubClientUserInfoResponse;
import com.dk0124.project.user.application.port.out.GithubApiPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class GithubUserJoinServiceTest {
    @Mock
    private GithubApiPort githubApiPort;

    @Mock
    private GithubUserJoinPort githubUserJoinPort;

    @InjectMocks
    private GithubUserJoinService githubUserJoinService;

    @Test
    void join_성공() {
        String code = "code123";
        GithubUserJoinRequest request = new GithubUserJoinRequest(code, "nickname");

        when(githubApiPort.callGithubUserInfoByCode(code))
                .thenReturn(new GitHubClientUserInfoResponse("","uniqueId123", "nickname","url","url"));
        when(githubUserJoinPort.isUniqueIdAvailable("uniqueId123")).thenReturn(true);
        when(githubUserJoinPort.isNickNameAvailable("nickname")).thenReturn(true);

        githubUserJoinService.join(request);

        verify(githubUserJoinPort).join(new JoinCommand("uniqueId123","","url","url","nickname"));
    }

    @Test
    void join_중복된_고유_ID_실패() {
        String code = "code123";
        GithubUserJoinRequest request = new GithubUserJoinRequest(code, "nickname");

        when(githubApiPort.callGithubUserInfoByCode(code))
                .thenReturn(new GitHubClientUserInfoResponse("","uniqueId123", "nickname","url","url"));
        when(githubUserJoinPort.isUniqueIdAvailable("uniqueId123")).thenReturn(false);

        assertThrows(JoinFailException.class, () -> githubUserJoinService.join(request));
    }

    @Test
    void join_중복된_닉네임_실패() {
        String code = "code123";
        GithubUserJoinRequest request = new GithubUserJoinRequest(code, "nickname");

        when(githubApiPort.callGithubUserInfoByCode(code))
                .thenReturn(new GitHubClientUserInfoResponse("","uniqueId123", "nickname","url","url"));
        when(githubUserJoinPort.isUniqueIdAvailable("uniqueId123")).thenReturn(true);
        when(githubUserJoinPort.isNickNameAvailable("nickname")).thenReturn(false);

        assertThrows(JoinFailException.class, () -> githubUserJoinService.join(request));
    }
}
