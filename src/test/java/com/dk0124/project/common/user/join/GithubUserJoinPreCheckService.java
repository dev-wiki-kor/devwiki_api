package com.dk0124.project.common.user.join;

import com.dk0124.project.user.application.port.out.GithubApiPort;
import com.dk0124.project.user.application.port.out.UserExistCheckPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubUserJoinPreCheckService implements GithubUserJoinPreCheckUsecase{

    private final GithubApiPort githubApiPort;

    private final UserExistCheckPort userExistCheckPort;

    @Override
    public boolean canRegister(String code) {

        var githubUserInfo = githubApiPort.callGithubUserInfoByCode(code);

        var userInfo = userExistCheckPort.findByGithubUniqueId(githubUserInfo.uniqueId());

        if(userInfo != null )
            return false;

        return true;
    }
}
