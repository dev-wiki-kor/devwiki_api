package com.dk0124.project.common.user.join;

public interface GithubUserJoinPort {
    public boolean isUniqueIdAvailable(String uniqueId);

    public boolean isNickNameAvailable(String uniqueId);

    public boolean join(String uniqueId, String nickname);
}
