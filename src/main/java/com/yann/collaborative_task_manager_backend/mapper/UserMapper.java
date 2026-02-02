package com.yann.collaborative_task_manager_backend.mapper;

import com.yann.collaborative_task_manager_backend.dto.authDTO.RegisterDTO;
import com.yann.collaborative_task_manager_backend.dto.userDTO.UserCreateDTO;
import com.yann.collaborative_task_manager_backend.dto.userDTO.UserDTO;
import com.yann.collaborative_task_manager_backend.entity.userEntity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdTasks", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    User toEntity(UserCreateDTO userCreateDTO);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdTasks", ignore = true)
    @Mapping(target = "enabled", constant = "true")
    User toEntity(RegisterDTO registerDTO);
}