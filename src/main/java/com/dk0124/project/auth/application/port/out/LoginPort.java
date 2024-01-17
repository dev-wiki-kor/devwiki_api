package com.dk0124.project.auth.application.port.out;

import com.dk0124.project.auth.domain.UserLoginInfo;

public interface LoginPort {
    public UserLoginInfo getLoginInfo(String userName);
}
