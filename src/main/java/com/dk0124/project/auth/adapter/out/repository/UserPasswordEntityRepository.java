package com.dk0124.project.auth.adapter.out.repository;

import com.dk0124.project.auth.adapter.out.entity.UserPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserPasswordEntityRepository extends JpaRepository<UserPasswordEntity, Long> {
    public Optional<UserPasswordEntity> findByUserId(Long userId);
}
