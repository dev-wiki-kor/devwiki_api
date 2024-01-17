package com.dk0124.project.common.user.adapter.out.user.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "USER_INFO_ENTITY")
public class UserInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_INFO_ENTITY")
    private Long id;

    @Column(name = "USER_META_ID")
    private Long userId;

    private String rank;

    private String nickname;
}
