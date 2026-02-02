package com.yann.collaborative_task_manager_backend.service;

import com.yann.collaborative_task_manager_backend.dto.taskDTO.TaskCreateDTO;
import com.yann.collaborative_task_manager_backend.repository.TaskRepository;
import com.yann.collaborative_task_manager_backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_ShouldThrowException_WhenOwnerNotFound() {
        // GIVEN
        TaskCreateDTO dto = new TaskCreateDTO();
        dto.setOwnerId(99L);
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> taskService.createTask(dto));
        verify(taskRepository, never()).save(any());
    }
}