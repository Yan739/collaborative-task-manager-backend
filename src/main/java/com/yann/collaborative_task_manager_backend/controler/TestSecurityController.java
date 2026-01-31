package com.yann.collaborative_task_manager_backend.controler;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestSecurityController {

    @GetMapping("/secured")

    public String secured() {
        return "ACCESS OK : Vous êtes bien authentifié.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminOnly() {
        return "ADMIN OK : Vous avez bien le rôle administrateur.";
    }
}