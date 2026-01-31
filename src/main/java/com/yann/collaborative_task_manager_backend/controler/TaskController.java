package com.yann.collaborative_task_manager_backend.controler;

import com.yann.collaborative_task_manager_backend.dto.TaskUpdateDTO;
import com.yann.collaborative_task_manager_backend.dto.taskDTO.TaskCreateDTO;
import com.yann.collaborative_task_manager_backend.dto.taskDTO.TaskDTO;
import com.yann.collaborative_task_manager_backend.dto.userDTO.UserDTO;
import com.yann.collaborative_task_manager_backend.dto.userDTO.UserUpdateDTO;
import com.yann.collaborative_task_manager_backend.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@Valid @RequestBody TaskCreateDTO dto) {
        return new ResponseEntity<>(taskService.createTask(dto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @Valid @RequestBody TaskUpdateDTO dto) {
        return ResponseEntity.ok(taskService.updateTask(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }
}