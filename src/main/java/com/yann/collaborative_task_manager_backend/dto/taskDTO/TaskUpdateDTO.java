package com.yann.collaborative_task_manager_backend.dto;

import com.yann.collaborative_task_manager_backend.entity.taskEntity.Priority;
import com.yann.collaborative_task_manager_backend.entity.taskEntity.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskUpdateDTO {

    @Size(min = 3, max = 100, message = "Le titre doit faire entre 3 et 100 caractères")
    private String title;

    private String description;

    private Status status;

    private Priority priority;

    @FutureOrPresent(message = "La date d'échéance ne peut pas être dans le passé")
    private LocalDateTime dueDate;

    private Long assignedToId;
}