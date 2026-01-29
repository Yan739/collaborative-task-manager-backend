package com.yann.collaborative_task_manager_backend.Mapper;

import com.yann.collaborative_task_manager_backend.dto.UserCreateDTO;
import com.yann.collaborative_task_manager_backend.dto.UserDTO;
import com.yann.collaborative_task_manager_backend.entity.Role;
import com.yann.collaborative_task_manager_backend.entity.User;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserMapper {

    public static UserDTO toDTO(User user) {
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setUserName(user.getUserName());
        userDTO.setEmail(user.getEmail());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setEnabled(user.isEnabled());
        userDTO.setRole(user.getRole());

        return userDTO;
    }

    public static User toEntity(UserCreateDTO dto) {
        User user = new User();

        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setCreatedAt(dto.getCreatedAt());
        user.setEnabled(dto.isEnabled());
        user.setRole(Role.valueOf(dto.getRole()));

        return user;
    }
}
