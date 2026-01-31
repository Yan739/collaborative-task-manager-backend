package com.yann.collaborative_task_manager_backend.controler;

import com.yann.collaborative_task_manager_backend.exception.InvalidJwtException;
import com.yann.collaborative_task_manager_backend.dto.authDTO.AuthResponseDTO;
import com.yann.collaborative_task_manager_backend.dto.authDTO.LoginDTO;
import com.yann.collaborative_task_manager_backend.dto.authDTO.RefreshTokenDTO;
import com.yann.collaborative_task_manager_backend.dto.authDTO.RegisterDTO;
import com.yann.collaborative_task_manager_backend.service.AuthService;
import com.yann.collaborative_task_manager_backend.service.BlacklistService;
import com.yann.collaborative_task_manager_backend.service.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final BlacklistService blacklistService;

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterDTO dto) {
        authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@Valid @RequestBody RefreshTokenDTO dto) {
        String newAccessToken = authService.refresh(dto);

        return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidJwtException("Authorization header missing or malformed");
        }

        String token = authHeader.substring(7);

        try {
            Instant expiry = jwtService.extractExpiration(token).toInstant();
            blacklistService.blacklist(token, expiry);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            throw new InvalidJwtException("Invalid JWT token");
        }
    }
}