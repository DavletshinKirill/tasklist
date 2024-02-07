package com.example.tasklist.web.controller;

import com.example.tasklist.domain.user.User;
import com.example.tasklist.service.AuthService;
import com.example.tasklist.service.UserService;
import com.example.tasklist.service.impl.AuthServiceImpl;
import com.example.tasklist.service.impl.UserServiceImpl;
import com.example.tasklist.web.DTO.auth.JwtRequest;
import com.example.tasklist.web.DTO.auth.JwtResponse;
import com.example.tasklist.web.DTO.user.UserDto;
import com.example.tasklist.web.DTO.validation.OnCreate;
import com.example.tasklist.web.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.authentication.preauth.j2ee.J2eeBasedPreAuthenticatedWebAuthenticationDetailsSource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
@Slf4j
public class AuthController {
    private final AuthServiceImpl authService;
    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @PostMapping("/login")
    public JwtResponse login(@Validated @RequestBody JwtRequest loginRequest) {
        log.info("This is a login method");
        return authService.login(loginRequest);
    }

   @PostMapping("/register")
   public UserDto register(@Validated(OnCreate.class) @RequestBody UserDto userDto) {
        User user = userMapper.toEntity(userDto);
        User createdUser = userService.create(user);
        return userMapper.toDTO(createdUser);
   }

   @PostMapping("/refresh")
    public JwtResponse refresh(@RequestBody String refreshToken) {
        return authService.refresh(refreshToken);
   }
}
