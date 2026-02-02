package com.yann.collaborative_task_manager_backend.mapper;

import com.yann.collaborative_task_manager_backend.dto.taskDTO.TaskCreateDTO;
import com.yann.collaborative_task_manager_backend.dto.taskDTO.TaskDTO;
import com.yann.collaborative_task_manager_backend.entity.taskEntity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    @Mapping(target = "assignedTo", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Task toEntity(TaskCreateDTO dto);

    @Mapping(source = "owner.id", target = "ownerId")
    @Mapping(source = "owner.userName", target = "ownerName")
    @Mapping(source = "assignedTo.id", target = "assignedToId")
    @Mapping(source = "assignedTo.userName", target = "assignedToName")
    TaskDTO toDto(Task task);
}