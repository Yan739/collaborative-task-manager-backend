package com.yann.collaborative_task_manager_backend.dto.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
public class UserUpdateDTO {
    @NotBlank
    private String UserName;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size
    private String password;

    @NotNull
    private String createdAt;

    @NotNull
    private boolean enabled;

    @NotBlank
    private String role;
}
