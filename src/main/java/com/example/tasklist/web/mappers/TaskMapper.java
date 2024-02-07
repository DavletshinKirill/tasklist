package com.example.tasklist.web.mappers;

import com.example.tasklist.domain.task.Task;
import com.example.tasklist.web.DTO.task.TaskDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDTO(Task task);
    List<TaskDto> toDTO(List<Task> task);

    Task toEntity(TaskDto taskDto);
}
