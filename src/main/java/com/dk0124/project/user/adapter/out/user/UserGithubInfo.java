package com.dk0124.project.user.adapter.out.user;


import com.dk0124.project.auth.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor(staticName = "of")
@Getter
@Setter
@NoArgsConstructor
public class UserGithubInfo {

    private Long userGithubInfoId;

    private Long userMetaId;

    private String githubUniqueId;

    private Set<UserRole> userRoles = new HashSet<>();

    private boolean active;

}