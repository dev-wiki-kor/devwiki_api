package com.dk0124.project.common.user.join.test;

import com.dk0124.project.common.user.join.GithubUserJoinPreCheckService;
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
    private UserExistCheckPort userExistCheckPort;

    @InjectMocks
    private GithubUserJoinPreCheckService githubUserJoinPreCheckService;

    @Test
    void canRegister_성공() {
        String code = "code123";
        when(githubApiPort.callGithubUserInfoByCode(code))
                .thenReturn(new GitHubClientUserInfoResponse("", "uniqueId123", "nickname", "url", "url"));
        when(userExistCheckPort.findByGithubUniqueId("uniqueId123")).thenReturn(null);

        assertTrue(githubUserJoinPreCheckService.canRegister(code));
    }

    @Test
    void canRegister_실패_이미_존재하는_유저() {
        String code = "code123";
        when(githubApiPort.callGithubUserInfoByCode(code))
                .thenReturn(new GitHubClientUserInfoResponse("", "uniqueId123", "nickname", "url", "url"));
        when(userExistCheckPort.findByGithubUniqueId("uniqueId123"))
                .thenReturn(UserGithubInfo.of(null, null, "uniqueId123", null, Set.of(UserStatus.NORMAL), true));

        assertFalse(githubUserJoinPreCheckService.canRegister(code));
    }
}
