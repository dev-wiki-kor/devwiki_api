package com.dk0124.project.user.adapter.out.user.repository;

import com.dk0124.project.user.adapter.out.user.entity.UserGithubInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserGithubInfoEntityRepository extends JpaRepository<UserGithubInfoEntity, Long> {
    Optional<UserGithubInfoEntity> findByGithubUniqueId(String githubUniqueId);
}
