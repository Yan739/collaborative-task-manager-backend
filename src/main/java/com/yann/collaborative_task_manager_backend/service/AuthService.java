package com.yann.collaborative_task_manager_backend.service;

import com.yann.collaborative_task_manager_backend.Mapper.UserMapper;
import com.yann.collaborative_task_manager_backend.dto.authDTO.AuthResponseDTO;
import com.yann.collaborative_task_manager_backend.dto.authDTO.LoginDTO;
import com.yann.collaborative_task_manager_backend.dto.authDTO.RefreshTokenDTO;
import com.yann.collaborative_task_manager_backend.dto.authDTO.RegisterDTO;
import com.yann.collaborative_task_manager_backend.entity.authEntity.RefreshToken;
import com.yann.collaborative_task_manager_backend.entity.userEntity.Role;
import com.yann.collaborative_task_manager_backend.entity.userEntity.User;
import com.yann.collaborative_task_manager_backend.repository.UserRepository;
import jakarta.annotation.Nonnull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, RefreshTokenService refreshTokenService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    public void register (@Nonnull RegisterDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        User user = UserMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.valueOf(dto.getRole()));

        userRepository.save(user);
    }

    public AuthResponseDTO login(@Nonnull LoginDTO dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Identifiants invalides"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Identifiants invalides");
        }

        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.create(user);

        AuthResponseDTO response = new AuthResponseDTO();
        response.accessToken = accessToken;
        response.refreshToken = refreshToken.getToken();

        return response;
    }

    public String refresh(RefreshTokenDTO dto) {

        RefreshToken token = refreshTokenService.verify(dto.refreshToken);
        return jwtService.generateToken(token.getUser());
    }



}
