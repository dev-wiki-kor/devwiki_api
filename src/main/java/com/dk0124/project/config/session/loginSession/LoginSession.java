package com.dk0124.project.config.session.loginSession;

import com.dk0124.project.user.domain.UserRole;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Set;


@AllArgsConstructor
public class LoginSession implements Serializable {
    Long userId;

    Set<UserRole> roles;
}
