package com.example.tasklist.repository.impl;

import com.example.tasklist.domain.exeption.ResourceMappingException;
import com.example.tasklist.domain.task.Task;
import com.example.tasklist.repository.DataSourceConfig;
import com.example.tasklist.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import com.example.tasklist.repository.mappers.TaskRowMapper;


@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {

    private final DataSourceConfig dataSourceConfig;

    private final String FIND_BY_ID = """
            SELECT  t.id as task_id,
            		t.title as task_title,
            		t.description as task_description,
            		t.status as task_status,
            		t.expirationData as task_expiration_data
            FROM tasklist.tasks t
            WHERE id = ?
            """;

    private final String FIND_BY_USER_ID = """
            SELECT  t.id as task_id,
            		t.title as task_title,
            		t.description as task_description,
            		t.status as task_status,
            		t.expirationData as task_expiration_data
            FROM tasklist.tasks t
            JOIN tasklist.user_tasks ut on t.id = ut.task_id
            WHERE ut.user_id = ?
            """;

    private final String ASSIGN = """
            INSERT INTO tasklist.user_tasks(task_id, user_id)
            VALUES(?, ?)
            """;

    private final String DELETE = """
            DELETE FROM tasklist.tasks
            WHERE id = ?
            """;
    private final String UPDATE = """
        UPDATE tasklist.tasks
        SET title = ?
            description = ?
            expirationData = ?
            status = ?
        WHERE id = ?
    """;

    private final String CREATE = """
            INSERT INTO tasklist.tasks(title, description, expirationData, status)
            VALUES(?, ?, ?, ?)
            """;
    @Override
    public Optional<Task> findById(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_USER_ID);
            preparedStatement.setLong(1, id);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                return Optional.ofNullable(TaskRowMapper.mapRow(rs));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding task by id");
        }
    }

    @Override
    public List<Task> findByUserId(Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setLong(1, userId);
            try(ResultSet rs = preparedStatement.executeQuery()) {
                return TaskRowMapper.mapRows(rs);
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while finding tasks by user id");
        }
    }

    @Override
    public void assignToUserById(Long taskId, Long userId) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(ASSIGN);
            preparedStatement.setLong(1, taskId);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while assigning to user");
        }
    }

    @Override
    public void update(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, task.getTitle());
            if(task.getDescription() == null) {
                preparedStatement.setNull(2, Types.VARCHAR);
            } else {
                preparedStatement.setString(2, task.getDescription());
            }

            if(task.getExpirationData() == null) {
                preparedStatement.setNull(3, Types.TIMESTAMP);
            } else {
                preparedStatement.setTimestamp(3, Timestamp.valueOf(task.getExpirationData()));
            }
            preparedStatement.setString(4, task.getStatus().name());
            preparedStatement.setLong(5, task.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while updating to user");
        }
    }

    @Override
    public void create(Task task) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(CREATE, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, task.getTitle());
            if(task.getDescription() == null) {
                preparedStatement.setNull(2, Types.VARCHAR);
            } else {
                preparedStatement.setString(2, task.getDescription());
            }

            if(task.getExpirationData() == null) {
                preparedStatement.setNull(3, Types.TIMESTAMP);
            } else {
                preparedStatement.setTimestamp(3, Timestamp.valueOf(task.getExpirationData()));
            }
            preparedStatement.setString(4, task.getStatus().name());
            preparedStatement.executeUpdate();
            try(ResultSet rs = preparedStatement.getGeneratedKeys()) {
                rs.next();
                task.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while creating to user");
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new ResourceMappingException("Error while deleting to user");
        }
    }
}
