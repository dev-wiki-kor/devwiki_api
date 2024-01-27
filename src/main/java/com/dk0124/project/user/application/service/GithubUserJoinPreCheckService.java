package com.dk0124.project.user.application.service;

import com.dk0124.project.user.domain.GithubUserCanJoinResult;
import com.dk0124.project.user.application.port.out.GithubApiPort;
import com.dk0124.project.user.application.GithubUserJoinPreCheckUsecase;
import com.dk0124.project.user.application.port.out.GithubUserJoinPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * 회원 가입 전, 중복된 github 계정이 있는지 확인 .
 *
 * 1) oauth code 로 유저 정보 , bearer token 호출 ( github api ) .
 *
 * 2) github unique id 에 해당하는 유저로 회원가입이 가능한지 확인 .
 *
 * 3)  bearerToken, 가능 여부를 응답으로 전송
 * */
@Service
@RequiredArgsConstructor
public class GithubUserJoinPreCheckService implements GithubUserJoinPreCheckUsecase {

    private final GithubApiPort githubApiPort;

    private final GithubUserJoinPort githubUserJoinPort;

    @Override
    public GithubUserCanJoinResult canRegister(String code) {
        // 1) oauth code 로 유저 정보 , bearer token 호출 ( github api ) .
        var bearerToken = githubApiPort.callAccessToken(code).getBearerToken();
        var githubUserInfo = githubApiPort.callUserInfo(bearerToken);
        //2) github unique id 에 해당하는 유저로 회원가입이 가능한지 확인 .
        var canJoin = githubUserJoinPort.isUniqueIdAvailable(githubUserInfo.uniqueId());
        //3)  bearerToken, 가능 여부를 응답으로 전송
        return new GithubUserCanJoinResult(canJoin, bearerToken);
    }
}
