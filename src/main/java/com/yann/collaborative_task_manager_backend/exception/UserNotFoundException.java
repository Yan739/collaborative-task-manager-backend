package com.yann.collaborative_task_manager_backend.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("Utilisateur avec l'id " + id + " introuvable");
    }
}
