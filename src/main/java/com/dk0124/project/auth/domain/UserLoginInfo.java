package com.dk0124.project.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor(staticName = "of")
public class UserLoginInfo {
    private final Long userId;
    private final String userName;
    private final String password;
    private final Set<UserRole> userRoles;
}
