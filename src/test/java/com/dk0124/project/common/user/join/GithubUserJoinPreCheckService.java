package com.dk0124.project.common.user.join;

import com.dk0124.project.user.adapter.out.github.GitHubClientUserInfo;
import com.dk0124.project.user.adapter.out.github.GithubClientAccessToken;
import com.dk0124.project.user.application.port.out.UserExistCheckPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubUserJoinPreCheckService implements GithubUserJoinPreCheckUsecase{

    private final GithubClientAccessToken githubClientAccessToken;
    private final GitHubClientUserInfo gitHubClientUserInfo;

    private final UserExistCheckPort userExistCheckPort;

    @Override
    public boolean canRegister(String code) {

        // TODO : 어댑터로 빼기 , id 값 지우기.
        var githubAccessTokenResponse
                = githubClientAccessToken.call(null, "c762b77b5518e2e55544",
                "8dca729660542f8a7038a0f234a231d4e7fa94aa", code);

        var githubUserInfo = gitHubClientUserInfo.call(githubAccessTokenResponse.getBearerToken());



        return false;
    }


}
