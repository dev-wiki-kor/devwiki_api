package com.dk0124.project.user.adapter.out.user.entity;


import com.dk0124.project.global.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_INFO_ENTITY")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UserProfileEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_PROFILE_ENTITY")
    private Long id;

    @Column(name = "USER_META_ID")
    private Long userMetaId;

    private String rank;

    private String nickname;

    public static UserProfileEntity of(Long userMetaId, String rank, String nickname) {
        return new UserProfileEntity(null, userMetaId, rank, nickname);
    }
}
