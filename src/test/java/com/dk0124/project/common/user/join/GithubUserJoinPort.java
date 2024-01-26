package com.dk0124.project.common.user.join;

public interface GithubUserJoinPort {
    boolean isUniqueIdAvailable(String uniqueId);

    boolean isNickNameAvailable(String uniqueId);

    void join(JoinCommand joinCommand);
}
