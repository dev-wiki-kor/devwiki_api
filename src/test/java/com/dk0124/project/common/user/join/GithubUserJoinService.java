package com.dk0124.project.common.user.join;


import com.dk0124.project.user.application.port.out.GithubApiPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GithubUserJoinService implements GithubUserJoinUsecase{
    private final GithubApiPort githubApiPort;

    private final GithubUserJoinPort githubUserJoinPort;

    @Override
    @Transactional
    public void join(GithubUserJoinRequest githubUserJoinRequest) {
        // get access token & user info from github
        var userInfoResponse
                = githubApiPort.callGithubUserInfoByCode(githubUserJoinRequest.code());

        // check if there is duplicate user
        if(!githubUserJoinPort.isUniqueIdAvailable(userInfoResponse.uniqueId()))
            throw new JoinFailException("이미 가입된 깃허브 계정");

        //check nickname is usable
        if(!githubUserJoinPort.isNickNameAvailable(githubUserJoinRequest.nickname()))
            throw new JoinFailException("이미 사용하고 있는 닉네임");

        // join user
        githubUserJoinPort.join(userInfoResponse.uniqueId(), githubUserJoinRequest.nickname());
    }
}
