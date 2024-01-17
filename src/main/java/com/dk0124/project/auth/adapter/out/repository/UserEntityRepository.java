package com.dk0124.project.auth.adapter.out.repository;

import com.dk0124.project.auth.adapter.out.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {
    public boolean existsByUserName(String userName);
    public Optional<UserEntity> findByUserName(String userName);
}
