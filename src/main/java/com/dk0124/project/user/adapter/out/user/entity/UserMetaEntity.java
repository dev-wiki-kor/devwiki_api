package com.dk0124.project.user.adapter.out.user.entity;

import com.dk0124.project.common.domain.BaseEntity;
import com.dk0124.project.user.domain.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class UserMetaEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_META_ID")
    private Long id;


    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID"))
    @Enumerated(EnumType.STRING)
    private Set<UserRole> userRoles = new HashSet<>();


    @ElementCollection(targetClass = UserStatus.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "USER_STATUS", joinColumns = @JoinColumn(name = "USER_ID"))
    @Column(name = "USER_STATUS")
    private Set<UserStatus> userStatus = new HashSet<>();


    @Column(name = "ACTIVE")
    private boolean active = true;
    /*
    private String username; -> 생략... 현재는 User name 을 두지 않을 예정 ( github 계정으로만 로그인 )
    * */

}