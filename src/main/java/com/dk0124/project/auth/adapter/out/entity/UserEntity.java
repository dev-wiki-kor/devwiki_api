package com.dk0124.project.auth.adapter.out.entity;

import com.dk0124.project.common.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;


    @Column
    private String userName;

    public UserEntity(String userName) {
        this.userName = userName;
    }
}
