package com.yann.collaborative_task_manager_backend.dto.taskDTO;

import com.yann.collaborative_task_manager_backend.entity.taskEntity.Priority;
import com.yann.collaborative_task_manager_backend.entity.taskEntity.Status;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskCreateDTO {

    @NotBlank(message = "Le titre est obligatoire")
    @Size(min = 3, max = 100, message = "Le titre doit faire entre 3 et 100 caractères")
    private String title;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @Builder.Default
    private Status status = Status.TO_DO;

    @Builder.Default
    private Priority priority = Priority.MEDIUM;

    @NotNull(message = "La date d'échéance est obligatoire")
    @FutureOrPresent(message = "La date d'échéance ne peut pas être dans le passé")
    private LocalDateTime dueDate;

    @NotNull(message = "L'ID du propriétaire est obligatoire")
    private Long ownerId;

    private Long assignedToId;
}