package com.yann.collaborative_task_manager_backend.Mapper;

import com.yann.collaborative_task_manager_backend.dto.UserCreateDTO;
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
    User toEntity(UserCreateDTO userCreateDTO);
}