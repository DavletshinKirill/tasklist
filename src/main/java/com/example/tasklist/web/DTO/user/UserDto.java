package com.example.tasklist.web.DTO.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import com.example.tasklist.web.DTO.validation.OnCreate;
import com.example.tasklist.web.DTO.validation.OnUpdate;
import org.hibernate.validator.constraints.Length;

@Data
public class UserDto {
    @NotNull(message = "Id must be not null", groups = {OnUpdate.class})
    private Long id;

    @NotNull(message = "Name must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max=255, message = "Name length must be smaller that 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @NotNull(message = "Username must be not null", groups = {OnCreate.class, OnUpdate.class})
    @Length(max=255, message = "Username length must be smaller that 255 symbols", groups = {OnCreate.class, OnUpdate.class})
    private String userName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null",  groups = {OnCreate.class, OnUpdate.class})
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password confirmation must be not null",  groups = {OnCreate.class})
    private String passwordConfirmation;
}
