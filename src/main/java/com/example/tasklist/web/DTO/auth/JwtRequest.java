package com.example.tasklist.web.DTO.auth;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class JwtRequest {

    @NotNull(message = "Username must be not null")
    private String username;

    @NotNull(message = "Username must be not null")
    private String password;
}
