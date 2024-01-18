package com.dk0124.project.common.user.adapter.out.user.repository;

import com.dk0124.project.common.user.adapter.out.user.entity.UserMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserMetaEntityRepository extends JpaRepository<UserMetaEntity, Long> {
    Optional<UserMetaEntity> findByIdAndActive(Long id, boolean active);
}
