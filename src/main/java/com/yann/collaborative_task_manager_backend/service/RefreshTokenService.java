package com.yann.collaborative_task_manager_backend.service;

import com.yann.collaborative_task_manager_backend.entity.authEntity.RefreshToken;
import com.yann.collaborative_task_manager_backend.entity.userEntity.User;
import com.yann.collaborative_task_manager_backend.repository.RefreshTokenRepository;
import com.yann.collaborative_task_manager_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final UserRepository userRepository;

    public RefreshTokenService(
            RefreshTokenRepository repository,
            UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Transactional
    public RefreshToken create(User user) {

        User managedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        repository.findByUser(managedUser)
                .ifPresent(repository::delete);

        RefreshToken token = new RefreshToken();
        token.setUser(managedUser);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));

        return repository.save(token);
    }

    @Transactional(readOnly = true)
    public RefreshToken verify(String token) {
        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token invalide"));

        if (refreshToken.getExpiryDate().isBefore(Instant.now())) {
            repository.delete(refreshToken);
            throw new RuntimeException("Refresh token expiré");
        }

        return refreshToken;
    }
}