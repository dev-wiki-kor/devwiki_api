package com.dk0124.project.user;

import com.dk0124.project.global.exception.GithubApiException;
import com.dk0124.project.user.adapter.out.github.*;
import com.dk0124.project.user.exception.GithubAuthFailException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GithubApiAdapterTest {

    @Mock
    GithubClientAccessToken githubClientAccessToken;

    @Mock
    GitHubClientUserInfo gitHubClientUserInfo;

    @InjectMocks
    GithubApiAdapter githubApiAdapter;

    private static final String FAIL_CODE = "FAIL";
    private static final String SUCCESS_CODE = "SUCCESS";


    @Test
    void 코드로_유저정보_호출_성공() {
        // Given
        var mockAccessTokenResponse = new GithubAccessTokenResponse("access-token", "", "");
        var mockUserInfoResponse = new GitHubClientUserInfoResponse("", "uniqueId", "testuser", "avatar_url", "html_url");

        when(githubClientAccessToken.call(any(), any(), eq(SUCCESS_CODE))).thenReturn(mockAccessTokenResponse);
        when(gitHubClientUserInfo.call("Bearer access-token")).thenReturn(mockUserInfoResponse);

        // When
        var result = githubApiAdapter.callGithubUserInfoByCode(SUCCESS_CODE);

        // Then
        assertNotNull(result);
        assertEquals(mockUserInfoResponse.nickname(), result.nickname());
        assertEquals(mockUserInfoResponse.uniqueId(), result.uniqueId());
    }

    @Test
    void 코드로_유저정보_호출_실패_통신중_아무_애러발생()  {
        // Given
        when(githubClientAccessToken.call(any(), any(), eq(FAIL_CODE)))
                .thenThrow(new RuntimeException("Failed to get access token"));

        // When & Then
        assertThrows(GithubApiException.class, () -> githubApiAdapter.callGithubUserInfoByCode(FAIL_CODE));
    }


}