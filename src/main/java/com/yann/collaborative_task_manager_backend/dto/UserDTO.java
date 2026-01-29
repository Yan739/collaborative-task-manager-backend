package com.yann.collaborative_task_manager_backend.dto;

import com.yann.collaborative_task_manager_backend.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String userName;
    private String email;
    private String createdAt;
    private boolean enabled;
    private Role role;

}
