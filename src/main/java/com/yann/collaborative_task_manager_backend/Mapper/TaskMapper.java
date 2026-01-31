package com.yann.collaborative_task_manager_backend.Mapper;

import com.yann.collaborative_task_manager_backend.dto.taskDTO.TaskCreateDTO;
import com.yann.collaborative_task_manager_backend.dto.taskDTO.TaskDTO;
import com.yann.collaborative_task_manager_backend.entity.taskEntity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface TaskMapper {

    TaskDTO toDto(Task task);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "owner.id", source = "ownerId")
    @Mapping(target = "assignedTo.id", source = "assignedToId")
    Task toEntity(TaskCreateDTO taskCreateDTO);
}
