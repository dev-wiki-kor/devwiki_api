package com.dk0124.project.user.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor(staticName = "of")
@Getter
@Setter
public class UserGithubInfo {

    private Long userGithubInfoId;

    private Long userMetaId;

    private String githubUniqueId;

    private Set<UserRole> userRoles = new HashSet<>();

    private boolean active;

}
