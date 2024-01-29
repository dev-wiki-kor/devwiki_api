package com.dk0124.project.user.application.service;


import com.dk0124.project.user.adapter.in.dto.GithubUserJoinRequest;
import com.dk0124.project.user.application.port.out.GithubApiPort;
import com.dk0124.project.user.application.port.out.GithubUserJoinPort;
import com.dk0124.project.user.application.GithubUserJoinUsecase;
import com.dk0124.project.user.domain.JoinCommand;
import com.dk0124.project.user.exception.AccountAlreadyExistException;
import com.dk0124.project.user.exception.NickNameAlreadyExistException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * JoinPreCheck api 에서 응답받은 bearer token 을 파라미터로 씀 .
 * <p>
 * 1) github 유저 정보 쿼리 .
 * <p>
 * 2) 닉네임 등록 가능여부 확인 .
 * <p>
 * 3) 회원 가입
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GithubUserJoinService implements GithubUserJoinUsecase {

    private final GithubApiPort githubApiPort;

    private final GithubUserJoinPort githubUserJoinPort;

    @Override
    @Transactional
    public void join(GithubUserJoinRequest githubUserJoinRequest) {
        // get access token & user info from github
        var userInfoResponse
                = githubApiPort.callUserInfo(githubUserJoinRequest.bearerToken());
        // check if there is duplicate user
        if (!githubUserJoinPort.isUniqueIdAvailable(userInfoResponse.uniqueId()))
            throw new AccountAlreadyExistException();

        //check nickname is usable
        if (!githubUserJoinPort.isNickNameAvailable(githubUserJoinRequest.nickname()))
            throw new NickNameAlreadyExistException();


        // join user
        githubUserJoinPort.join(new JoinCommand(
                userInfoResponse.uniqueId(),
                userInfoResponse.email(),
                userInfoResponse.pageUrl(),
                userInfoResponse.profileUrl(),
                githubUserJoinRequest.nickname()
        ));
    }
}
