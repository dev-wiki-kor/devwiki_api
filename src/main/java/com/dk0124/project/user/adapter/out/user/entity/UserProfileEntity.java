package com.dk0124.project.user.adapter.out.user.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "USER_INFO_ENTITY")
public class UserProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_PROFILE_ENTITY")
    private Long id;

    @Column(name = "USER_META_ID")
    private Long userId;

    private String rank;

    private String nickname;
}
