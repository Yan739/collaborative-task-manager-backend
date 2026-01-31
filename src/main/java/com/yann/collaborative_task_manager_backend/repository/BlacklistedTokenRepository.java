package com.yann.collaborative_task_manager_backend.repository;

import com.yann.collaborative_task_manager_backend.entity.authEntity.BlacklistedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, Long> {
    boolean existsByToken(String token);
    void deleteByExpiryDateBefore(Instant now);
}