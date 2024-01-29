package com.dk0124.project.global.config.security.session;

import com.dk0124.project.user.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Set;


@AllArgsConstructor
@Getter
public class LoginSession implements Serializable {
    Long userId;
    Set<UserRole> roles;
}
