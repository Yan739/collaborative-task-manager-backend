package com.yann.collaborative_task_manager_backend.repository;

import com.yann.collaborative_task_manager_backend.entity.RefreshToken;
import com.yann.collaborative_task_manager_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository
        extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);
}

