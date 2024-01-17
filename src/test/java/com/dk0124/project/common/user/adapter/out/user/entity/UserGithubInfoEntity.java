package com.dk0124.project.common.user.adapter.out.user.entity;


import com.dk0124.project.common.domain.BaseEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "USER_GITHUB_INFO")
public class UserGithubInfoEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_GITHUB_INFO_ID")
    private Long id;

    @Column(name = "USER_META_ID")
    private Long userId;

    @Column(name = "GITHUB_UNIQUE_ID")
    private String githubUniqueId;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "GITHUB_URL")
    private String githubUrl;

    @Column(name = "USE_GITHUB_URL")
    private boolean useGithubUrl = true;
}
