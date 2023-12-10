package com.dk0124.project.user.application.port.in;

import com.dk0124.project.user.domain.UserLoginInfo;

public interface LoginUsercase {

    public UserLoginInfo login(String userName, String password);
}
