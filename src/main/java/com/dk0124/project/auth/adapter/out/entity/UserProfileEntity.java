package com.dk0124.project.auth.adapter.out.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class UserProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_profile_id")
    private Long id;


    @Column(name = "user_id")
    private Long userId;

    public UserProfileEntity(Long userId) {
        this.userId = userId;
    }
}
