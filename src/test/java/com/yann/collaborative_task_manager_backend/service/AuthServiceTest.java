package com.yann.collaborative_task_manager_backend.service;

import com.yann.collaborative_task_manager_backend.dto.authDTO.RegisterDTO;
import com.yann.collaborative_task_manager_backend.entity.userEntity.User;
import com.yann.collaborative_task_manager_backend.mapper.UserMapper;
import com.yann.collaborative_task_manager_backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthService authService;

    private RegisterDTO registerDTO;

    @BeforeEach
    void setUp() {
        registerDTO = new RegisterDTO();
        registerDTO.setEmail("test@example.com");
        registerDTO.setUserName("yann");
        registerDTO.setPassword("password123");
        registerDTO.setRole("USER");
    }

    @Test
    void register_ShouldThrowException_WhenEmailAlreadyExists() {
        // GIVEN
        when(userRepository.existsByEmail(registerDTO.getEmail())).thenReturn(true);

        // WHEN & THEN
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.register(registerDTO);
        });

        assertEquals("Cet email est déjà utilisé", exception.getMessage());

        verify(userRepository, never()).save(any());
    }

    @Test
    void register_ShouldSaveUser_WhenDataIsValid() {
        // GIVEN
        User user = new User();
        user.setEmail(registerDTO.getEmail());

        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userMapper.toEntity(any(RegisterDTO.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // WHEN
        authService.register(registerDTO);

        // THEN
        verify(passwordEncoder).encode("password123");
        verify(userRepository).save(user);
        assertEquals("encodedPassword", user.getPassword());
    }
}