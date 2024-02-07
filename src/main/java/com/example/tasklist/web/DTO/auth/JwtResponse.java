package com.example.tasklist.web.DTO.auth;

import lombok.Data;

@Data
public class JwtResponse {
    private long id;
    private String username;
    private String accessToken;
    private String freshToken;
}
