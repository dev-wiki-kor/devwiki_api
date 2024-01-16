package com.dk0124.project.common.user;

import com.dk0124.project.common.user.application.GithubLoginUsecase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
class LoginUsecaseTest {


    GithubLoginUsecase githubLoginUsecase;

    public LoginUsecaseTest( GithubLoginUsecase  githubLoginUsecase){
        this. githubLoginUsecase =  githubLoginUsecase;
    }


    @Test
    void 로그인_정상_실행(){

    }

    @Test
    void 로그인_실패_github_인증실패(){

    }

    @Test
    void 로그인_실패_유저정보_불러오기_실패(){

    }

    @Test
    void 로그인_실패_세션생성_실패(){

    }
}
