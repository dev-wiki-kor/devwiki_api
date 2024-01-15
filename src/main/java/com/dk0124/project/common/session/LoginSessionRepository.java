package com.dk0124.project.common.session;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoginSessionRepository extends CrudRepository<LoginSession, UUID> {
    public Optional<LoginSession> findByUuid(UUID uuid);
}
