package com.dk0124.project.user.application.port.out;

import com.dk0124.project.user.domain.UserGithubInfo;

public interface UserExistCheckPort {
    UserGithubInfo findByGithubUniqueId(String githubUniqueId);
}
