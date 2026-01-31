package com.yann.collaborative_task_manager_backend.entity.taskEntity;

import jakarta.persistence.*;
import lombok.Data;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;



@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status = Status.TO_DO;

    @Enumerated(EnumType.STRING)
    private Priority priority = Priority.MEDIUM;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;


    // La personne qui a créé la tâche
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    // La personne à qui la tâche est assignée
    @ManyToOne
    @JoinColumn(name = "assignee_id")
    private User assignedTo;

}
