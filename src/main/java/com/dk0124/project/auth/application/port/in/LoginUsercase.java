package com.dk0124.project.auth.application.port.in;

import com.dk0124.project.auth.domain.UserLoginInfo;

public interface LoginUsercase {

    public UserLoginInfo login(String userName, String password);
}
