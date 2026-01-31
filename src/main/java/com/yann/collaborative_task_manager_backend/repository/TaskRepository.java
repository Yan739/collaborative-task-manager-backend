package com.yann.collaborative_task_manager_backend.repository;

import com.yann.collaborative_task_manager_backend.entity.taskEntity.Status;
import com.yann.collaborative_task_manager_backend.entity.taskEntity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByOwnerId(Long ownerId);

    List<Task> findByAssignedToId(Long assigneeId);

    List<Task> findByStatus(Status status);

    List<Task> findByAssignedToIdAndStatus(Long assigneeId, Status status);
}