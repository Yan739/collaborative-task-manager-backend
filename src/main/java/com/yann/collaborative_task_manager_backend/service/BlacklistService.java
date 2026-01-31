package com.yann.collaborative_task_manager_backend.service;

import com.yann.collaborative_task_manager_backend.entity.authEntity.BlacklistedToken;
import com.yann.collaborative_task_manager_backend.repository.BlacklistedTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class BlacklistService {

    private final BlacklistedTokenRepository repository;

    @Transactional
    public void blacklist(String token, Instant expiryDate) {

        if (!repository.existsByToken(token)) {
            BlacklistedToken blacklisted = new BlacklistedToken();
            blacklisted.setToken(token);
            blacklisted.setExpiryDate(expiryDate);
            repository.save(blacklisted);
        }
    }

    public boolean isBlacklisted(String token) {
        return repository.existsByToken(token);
    }

    //nettoyage des tokens toutes les heures
    @Scheduled(cron = "0 0 * * * *")
    @Transactional
    public void clearExpiredTokens() {
        repository.deleteByExpiryDateBefore(Instant.now());
    }
}