package com.dk0124.project.auth.adapter.out.repository;

import com.dk0124.project.auth.adapter.out.entity.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileEntityRespository extends JpaRepository<UserProfileEntity, Long> {
}
