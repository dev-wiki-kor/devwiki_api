package com.dk0124.project.user.adapter.out.user.repository;

import com.dk0124.project.user.adapter.out.user.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfileEntityRepository extends JpaRepository<UserProfileEntity, Long> {
    boolean existsByNickname(String nickname);
    Optional<UserProfileEntity> findByUserMetaId(Long id);

}
