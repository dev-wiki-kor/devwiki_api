package com.dk0124.project.user.adapter.out.user.entity;


import com.dk0124.project.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USER_PROFILE_ENTITY")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@NoArgsConstructor
public class UserProfileEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_PROFILE_ENTITY")
    private Long id;

    @Column(name = "USER_META_ID")
    private Long userMetaId;

    @Column(name = "USER_RANK")
    private String rank;

    private String nickname;

    public static UserProfileEntity of(Long userMetaId, String rank, String nickname) {
        return new UserProfileEntity(null, userMetaId, rank, nickname);
    }
}
