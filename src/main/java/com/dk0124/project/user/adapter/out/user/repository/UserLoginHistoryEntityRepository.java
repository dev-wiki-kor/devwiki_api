package com.dk0124.project.user.adapter.out.user.repository;

import com.dk0124.project.user.adapter.out.user.entity.UserLoginHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoginHistoryEntityRepository extends JpaRepository<UserLoginHistoryEntity, Long> {
}
