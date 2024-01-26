package com.dk0124.project.user.adapter.out.user.entity;

import com.dk0124.project.user.domain.UserActionType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
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
