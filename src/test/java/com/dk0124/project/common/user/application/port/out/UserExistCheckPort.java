package com.dk0124.project.common.user.application.port.out;

import com.dk0124.project.common.user.adapter.out.user.User;
import com.dk0124.project.common.user.adapter.out.user.UserGithubInfo;

import java.util.Optional;

public interface UserExistCheckPort {
    UserGithubInfo findByGithubUniqueId(String githubUniqueId);
}
