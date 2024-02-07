package com.example.tasklist.service;

import com.example.tasklist.domain.user.User;

import java.sql.SQLException;


public interface UserService {
    User getById(Long id);
    User getByUserName(String userName);
    User update(User user);
    User create(User user);

    boolean isTaskOwner(Long userId, Long taskId);

    void delete(Long id);
}
