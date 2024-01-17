package com.dk0124.project.auth.adapter.out.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class UserPasswordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_password_id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;


    @Column(name = "password")
    private String password;

    public UserPasswordEntity(Long userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
