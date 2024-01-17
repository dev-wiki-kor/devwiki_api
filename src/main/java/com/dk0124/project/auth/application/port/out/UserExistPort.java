package com.dk0124.project.auth.application.port.out;

public interface UserExistPort {
    public boolean existByUserName(String userName);
    public boolean existByUserId(Long userId);
}
