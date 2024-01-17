package com.dk0124.project.common.user.application.port.out;

import com.dk0124.project.common.user.adapter.out.user.User;

import java.util.Optional;

public interface UserExistCheckPort {
    Optional<User> findByGithubUniqueId(String githubUniqueId);
}
