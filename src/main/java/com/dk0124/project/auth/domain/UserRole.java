package com.dk0124.project.auth.domain;

import java.util.Arrays;

public enum UserRole {

    USER, ADMIN, SYSTEM;

    String name;

    public UserRole of(String param) {
        return Arrays.stream(UserRole.values()).filter(
                e -> e.name.equals(param)
        ).findFirst()
                .orElseThrow(() -> new RuntimeException("No Matching Role"));
    }

}
