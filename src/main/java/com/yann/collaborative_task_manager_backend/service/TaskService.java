package com.yann.collaborative_task_manager_backend.service;

import com.yann.collaborative_task_manager_backend.dto.TaskUpdateDTO;
import com.yann.collaborative_task_manager_backend.dto.taskDTO.TaskCreateDTO;
import com.yann.collaborative_task_manager_backend.dto.taskDTO.TaskDTO;
import com.yann.collaborative_task_manager_backend.entity.taskEntity.Task;
import com.yann.collaborative_task_manager_backend.entity.userEntity.User;
import com.yann.collaborative_task_manager_backend.exception.UserNotFoundException;
import com.yann.collaborative_task_manager_backend.mapper.TaskMapper;
import com.yann.collaborative_task_manager_backend.repository.TaskRepository;
import com.yann.collaborative_task_manager_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskDTO createTask(TaskCreateDTO dto) {

        Task task = taskMapper.toEntity(dto);

        User owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new UserNotFoundException(dto.getOwnerId()));
        task.setOwner(owner);

        if (dto.getAssignedToId() != null) {
            User assignee = userRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new UserNotFoundException(dto.getAssignedToId()));
            task.setAssignedTo(assignee);
        }

        Task savedTask = taskRepository.save(task);

        return taskMapper.toDto(savedTask);
    }

    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(taskMapper::toDto)
                .collect(Collectors.toList());
    }

    public TaskDTO getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(taskMapper::toDto)
                .orElseThrow(() -> new RuntimeException("Tâche non trouvée avec l'ID : " + id));
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskUpdateDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tâche introuvable"));

        if (dto.getTitle() != null) task.setTitle(dto.getTitle());
        if (dto.getDescription() != null) task.setDescription(dto.getDescription());
        if (dto.getStatus() != null) task.setStatus(dto.getStatus());
        if (dto.getPriority() != null) task.setPriority(dto.getPriority());
        if (dto.getDueDate() != null) task.setDueDate(dto.getDueDate());

        if (dto.getAssignedToId() != null) {
            User newAssignee = userRepository.findById(dto.getAssignedToId())
                    .orElseThrow(() -> new UserNotFoundException(dto.getAssignedToId()));
            task.setAssignedTo(newAssignee);
        }

        return taskMapper.toDto(taskRepository.save(task));
    }

    @Transactional
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Tâche introuvable");
        }
        taskRepository.deleteById(id);
    }
}