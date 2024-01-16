package com.dk0124.project.common.user;

import com.dk0124.project.common.user.adapter.out.user.User;

public interface CreateLoginSession {
    LoginSession create(User user);
}
