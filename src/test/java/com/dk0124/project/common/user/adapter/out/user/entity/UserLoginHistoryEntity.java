package com.dk0124.project.common.user.adapter.out.user.entity;

import com.dk0124.project.common.user.adapter.out.user.UserActionType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserLoginHistoryEntity {

    @Id
    @GeneratedValue
    @Column(name = "USER_LOGIN_HISTORY_ENTITY")
    private Long id;

    private Long userId;

    @Enumerated
    private UserActionType userActionType;

    private LocalDateTime doWhen;

    public static UserLoginHistoryEntity of(Long userId, UserActionType userActionType, LocalDateTime doWhen) {
        return new UserLoginHistoryEntity(null, userId, userActionType, doWhen);
    }
}
