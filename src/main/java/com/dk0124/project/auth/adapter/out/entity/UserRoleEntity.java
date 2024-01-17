package com.dk0124.project.auth.adapter.out.entity;

import com.dk0124.project.auth.domain.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "user_id")
    public Long userId;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    public UserRole userRole;

    public UserRoleEntity(Long userId, UserRole userRole) {
        this.userId = userId;
        this.userRole = userRole;
    }
}
