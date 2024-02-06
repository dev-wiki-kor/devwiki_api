package com.dk0124.project.user.adapter.out.user.entity;


import com.dk0124.project.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_GITHUB_INFO")
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserGithubInfoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_GITHUB_INFO_ID")
    private Long id;

    @Column(name = "USER_META_ID")
    private Long userMetaId;

    @Column(name = "GITHUB_UNIQUE_ID")
    private String githubUniqueId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "GITHUB_URL")
    private String githubUrl;

    @Column(name = "USE_GITHUB_URL")
    private boolean useGithubUrl = true;

    public static UserGithubInfoEntity of(Long userMetaId, String githubUniqueId, String email, String githubUrl, boolean useGithubUrl) {
        return new UserGithubInfoEntity(null, userMetaId, githubUniqueId, email, githubUrl, useGithubUrl);
    }
}
