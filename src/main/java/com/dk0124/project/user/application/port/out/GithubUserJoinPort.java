package com.dk0124.project.user.application.port.out;

import com.dk0124.project.user.domain.JoinCommand;

public interface GithubUserJoinPort {
    boolean isUniqueIdAvailable(String uniqueId);

    boolean isNickNameAvailable(String uniqueId);

    void join(JoinCommand joinCommand);
}
