package com.yann.collaborative_task_manager_backend.dto.userDTO;

import com.yann.collaborative_task_manager_backend.entity.userEntity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String userName;
    private String email;
    private LocalDateTime createdAt;
    private boolean enabled;
    private Role role;
}