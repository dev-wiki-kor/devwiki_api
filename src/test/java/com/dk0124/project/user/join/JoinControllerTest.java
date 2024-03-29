package com.dk0124.project.user.join;

import com.dk0124.project.global.exception.GithubApiException;
import com.dk0124.project.user.adapter.in.GithubJoinController;
import com.dk0124.project.user.adapter.in.dto.GithubUserJoinRequest;
import com.dk0124.project.user.domain.GithubUserCanJoinResult;
import com.dk0124.project.user.application.port.in.GithubUserJoinPreCheckUsecase;
import com.dk0124.project.user.application.port.in.GithubUserJoinUsecase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// TODO : controller advice 적용 .
// security 관련 설정은 통합 테스트에서
@WebMvcTest(GithubJoinController.class)
public class JoinControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GithubUserJoinUsecase githubUserJoinUsecase;

    @MockBean
    private GithubUserJoinPreCheckUsecase githubUserJoinPreCheckUsecase;


    @Test
    void empty() {
        assertNotNull(mockMvc);
        assertNotNull(githubUserJoinPreCheckUsecase);
        assertNotNull(githubUserJoinPreCheckUsecase);
    }

    @Test
    @WithMockUser /* TODO : 인증 구현 후 제거 */
    void 로그인_가능여부_검사_api_성공() throws Exception {

        String code = "testCode";
        GithubUserCanJoinResult res = new GithubUserCanJoinResult(true, "accessToken");
        when(githubUserJoinPreCheckUsecase.canRegister(code)).thenReturn(res);

        mockMvc.perform(post("/v1/user/join/checkCode")
                .content("{\"code\":\"" + code + "\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"success\":true,\"bearerToken\":\"accessToken\"}"))
                .andDo(print());

        verify(githubUserJoinPreCheckUsecase, times(1)).canRegister(code);
    }

    @Test
    @WithMockUser
    void 회원가입_가능여부_검사_api_실패() throws Exception {
        String code = "code";
        GithubUserCanJoinResult res = new GithubUserCanJoinResult(false, "accessToken");
        when(githubUserJoinPreCheckUsecase.canRegister(code)).thenReturn(res);

        mockMvc.perform(post("/v1/user/join/checkCode")
                .content("{\"code\":\"" + code + "\",\"nickname\":\"nickname\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"success\":false,\"bearerToken\":\"accessToken\"}"))
                .andDo(print());

        verify(githubUserJoinPreCheckUsecase, times(1)).canRegister(code);
    }

    @Test
    @WithMockUser
    void 사용자_가입_api_성공() throws Exception {
        String bearerToken = "testCode";
        String nickname = "testNickname";

        doNothing().when(githubUserJoinUsecase).join(any(GithubUserJoinRequest.class));

        mockMvc.perform(post("/v1/user/join/github")
                .content("{\"bearerToken\":\"" + bearerToken + "\", \"nickname\":\"" + nickname + "\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        ).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{\"success\":true}"))
                .andDo(print());

        verify(githubUserJoinUsecase, times(1)).join(any(GithubUserJoinRequest.class));
    }

    @Test
    @WithMockUser
    void 사용자_가입_api_실패() throws Exception {
        String bearerToken = "testCode";
        String nickname = "testNickname";

        doThrow(new GithubApiException()).when(githubUserJoinUsecase).join(any(GithubUserJoinRequest.class));

        mockMvc.perform(post("/v1/user/join/github")
                .content("{\"bearerToken\":\"" + bearerToken + "\", \"nickname\":\"" + nickname + "\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .with(csrf())
        ).andExpect(status().is4xxClientError())
                .andDo(print());

        verify(githubUserJoinUsecase, times(1)).join(any(GithubUserJoinRequest.class));
    }
}
