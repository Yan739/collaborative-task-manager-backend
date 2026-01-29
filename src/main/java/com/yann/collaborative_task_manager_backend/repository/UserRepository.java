package com.yann.collaborative_task_manager_backend.repository;

import com.yann.collaborative_task_manager_backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String Email);
    boolean existsByEmail(String Email);
}
