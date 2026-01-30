package com.yann.collaborative_task_manager_backend.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    public String accessToken;
    public String refreshToken;
}
