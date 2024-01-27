package com.dk0124.project.common.user.login;


import com.dk0124.project.user.adapter.in.LoginController;
import com.dk0124.project.user.adapter.in.dto.GithubLoginRequest;
import com.dk0124.project.user.application.GithubLoginUsecase;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GithubLoginUsecase githubLoginUsecase;


    @Test
    void empty(){}

    @Test
    @WithMockUser /* TODO : 인증 구현 후 제거 */
    void 깃허브_로그인_성공() throws Exception {
        // githubLoginUsecase.login() 메소드가 아무것도 하지 않도록 설정
        doNothing().when(githubLoginUsecase).login(any(GithubLoginRequest.class));

        // POST 요청 수행 ("/v1/user/login/github")
        mockMvc.perform(post("/v1/user/login/github")
                .contentType("application/json")  // JSON 형식으로 전송
                .content("{\"code\": \"code\"}")  // JSON 데이터 입력
                .header("myCookie", "cookie coookie")
                .with(csrf()))
                .andExpect(status().isOk());
    }

}
