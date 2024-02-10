package com.happyfxmas.erdbsystem.modules.tasks.api.mapper;


import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.TaskDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.request.CreateTaskRequestDTO;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Task;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.enums.Complexity;

public class TaskDTOMapper {

    public static TaskDTO makeDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .complexity(task.getComplexity())
                .testDataAmount(task.getTestDataAmount())
                .build();
    }

    public static TaskDTO makeDTO(CreateTaskRequestDTO createTaskRequestDTO) {
        return TaskDTO.builder()
                .title(createTaskRequestDTO.getTitle())
                .description(createTaskRequestDTO.getDescription())
                .complexity(Complexity.fromString(createTaskRequestDTO.getComplexity()))
                .testDataAmount(createTaskRequestDTO.getTestDataAmount())
                .build();
    }

    public static Task fromDTO(TaskDTO taskDTO) {
        return Task.builder()
                .title(taskDTO.getTitle())
                .description(taskDTO.getDescription())
                .complexity(taskDTO.getComplexity())
                .testDataAmount(taskDTO.getTestDataAmount())
                .build();
    }

    public static Task fromDTO(CreateTaskRequestDTO createTaskRequestDTO) {
        return Task.builder()
                .title(createTaskRequestDTO.getTitle())
                .description(createTaskRequestDTO.getDescription())
                .complexity(Complexity.fromString(createTaskRequestDTO.getComplexity()))
                .testDataAmount(createTaskRequestDTO.getTestDataAmount())
                .build();
    }
}
