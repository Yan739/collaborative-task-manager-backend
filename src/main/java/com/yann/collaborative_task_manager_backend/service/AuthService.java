package com.yann.collaborative_task_manager_backend.service;

import com.yann.collaborative_task_manager_backend.mapper.UserMapper;
import com.yann.collaborative_task_manager_backend.dto.authDTO.AuthResponseDTO;
import com.yann.collaborative_task_manager_backend.dto.authDTO.LoginDTO;
import com.yann.collaborative_task_manager_backend.dto.authDTO.RefreshTokenDTO;
import com.yann.collaborative_task_manager_backend.dto.authDTO.RegisterDTO;
import com.yann.collaborative_task_manager_backend.entity.authEntity.RefreshToken;
import com.yann.collaborative_task_manager_backend.entity.userEntity.Role;
import com.yann.collaborative_task_manager_backend.entity.userEntity.User;
import com.yann.collaborative_task_manager_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void register(RegisterDTO dto) {

        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        User user = userMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.valueOf(dto.getRole().toUpperCase()));
        user.setEnabled(true);

        userRepository.save(user);
    }

    public AuthResponseDTO login(LoginDTO dto) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword())
        );

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé après authentification"));

        String accessToken = jwtService.generateToken(user);
        RefreshToken refreshToken = refreshTokenService.create(user);

        return new AuthResponseDTO(accessToken, refreshToken.getToken());
    }

    @Transactional
    public String refresh(RefreshTokenDTO dto) {
        RefreshToken token = refreshTokenService.verify(dto.getRefreshToken());
        return jwtService.generateToken(token.getUser());
    }
}