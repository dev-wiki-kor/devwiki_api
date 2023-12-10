package com.dk0124.project.user.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class UserLoginInfo {
    private final Long userId;
    private final String userName;
    private final String password;
}
