package com.dk0124.project.common.user;


import com.dk0124.project.common.user.adapter.out.github.GitHubClientUserInfo;
import com.dk0124.project.common.user.adapter.out.github.GithubClientAccessToken;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.common.net.HttpHeaders;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

/*
 TODO : WIREMOCK boot 3.2 와 버전 에러있어서 잠시 보류,
 stand alone wiremock or 다른 의존 으로 넘어갈 것을 대비해 기록만 남김
 disabled 처리
 */

@AutoConfigureWebClient(registerRestTemplate = true)
@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@Disabled
public class GithubFeignClientTest {

    static WireMockServer wireMockServer = new WireMockServer(
            options().dynamicPort().stubCorsEnabled(true)
    );

    @Autowired
    private GitHubClientUserInfo gitHubClientUserInfo;

    @Autowired
    private GithubClientAccessToken githubClientAccessToken;

    @Test
    void 유저정보_요청_성공() {
        // Given
        stubFor(get(urlEqualTo("/user"))
                .withHeader(HttpHeaders.AUTHORIZATION, equalTo("your-access-token"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"login\":\"testuser\",\"id\":123,\"name\":\"Test User\"}")));
        // When
        GithubUserInfo userInfo = gitHubClientUserInfo.call("your-access-token");

        // Then
    }

    @Test
    void 유저정보_요청_실패() {
        // Given
        stubFor(get(urlEqualTo("/user"))
                .willReturn(aResponse()
                        .withStatus(404)));

        // When & Then
        // Handle the expected Feign exception or assert as needed in case of failure
        /*
        assertThatThrownBy(() -> gitHubClientUserInfo.call("your-access-token"))
                .isInstanceOf(FeignException.class)
                .hasMessageContaining("status 404");
         */
    }

    @Test
    void 토큰_요청_성공() {
        // Given
        stubFor(post(urlEqualTo("/login/oauth/access_token"))
                .withHeader(HttpHeaders.COOKIE, equalTo("your-cookie"))
                .withRequestBody(equalTo("client_id=your-client-id&client_secret=your-client-secret&code=your-code"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withBody("{\"access_token\":\"your-access-token\",\"token_type\":\"bearer\"}")));
        // When
        GithubAccessTokenResponse accessTokenResponse = githubClientAccessToken.call("your-cookie", "your-client-id", "your-client-secret", "your-code");

    }

    @Test
    void 토큰_요청_실패() {
        // Given
        stubFor(post(urlEqualTo("/login/oauth/access_token"))
                .willReturn(aResponse()
                        .withStatus(500)));
    }

}
