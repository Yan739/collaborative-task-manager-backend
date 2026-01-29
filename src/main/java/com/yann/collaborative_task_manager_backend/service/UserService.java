package com.yann.collaborative_task_manager_backend.service;

import com.yann.collaborative_task_manager_backend.Exception.UserNotFoundException;
import com.yann.collaborative_task_manager_backend.Mapper.UserMapper;
import com.yann.collaborative_task_manager_backend.dto.UserCreateDTO;
import com.yann.collaborative_task_manager_backend.dto.UserDTO;
import com.yann.collaborative_task_manager_backend.dto.UserUpdateDTO;
import com.yann.collaborative_task_manager_backend.entity.Role;
import com.yann.collaborative_task_manager_backend.entity.User;
import com.yann.collaborative_task_manager_backend.repository.UserRepository;
import jakarta.annotation.Nonnull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDTO save(UserCreateDTO dto) {
        User user = UserMapper.toEntity(dto);

        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        User savedUser = userRepository.save(user);
        return UserMapper.toDTO(savedUser);
    }

    public UserDTO update(Long id, @Nonnull UserUpdateDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setRole(Role.valueOf(dto.getRole()));
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return UserMapper.toDTO(userRepository.save(user));
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        userRepository.deleteById(id);
    }


    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return UserMapper.toDTO(user);
    }

}
