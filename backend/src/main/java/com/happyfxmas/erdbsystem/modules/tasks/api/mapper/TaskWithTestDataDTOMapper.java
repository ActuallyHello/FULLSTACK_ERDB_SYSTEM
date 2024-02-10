package com.happyfxmas.erdbsystem.modules.tasks.api.mapper;


import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.TestDataDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.response.TaskWithTestDataDTO;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Task;

public class TaskWithTestDataDTOMapper {

    public static TaskWithTestDataDTO makeDTO(Task task, TestDataDTO testDataDTO) {
        return TaskWithTestDataDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .complexity(task.getComplexity())
                .testDataAmount(task.getTestDataAmount())
                .testDataDTO(testDataDTO)
                .build();
    }
}
