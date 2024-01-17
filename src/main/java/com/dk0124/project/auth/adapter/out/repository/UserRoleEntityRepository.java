package com.dk0124.project.auth.adapter.out.repository;

import com.dk0124.project.auth.adapter.out.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRoleEntityRepository extends JpaRepository<UserRoleEntity, Long> {

    Set<UserRoleEntity> findAllByUserId(Long userId);
}
