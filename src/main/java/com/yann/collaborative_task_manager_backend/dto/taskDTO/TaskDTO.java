package com.yann.collaborative_task_manager_backend.dto.taskDTO;

import com.yann.collaborative_task_manager_backend.dto.userDTO.UserDTO;
import com.yann.collaborative_task_manager_backend.entity.taskEntity.Priority;
import com.yann.collaborative_task_manager_backend.entity.taskEntity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDTO {

    private Long id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private LocalDateTime dueDate;
    private LocalDateTime createdAt;

    private UserDTO owner;
    private UserDTO assignedTo;
}
