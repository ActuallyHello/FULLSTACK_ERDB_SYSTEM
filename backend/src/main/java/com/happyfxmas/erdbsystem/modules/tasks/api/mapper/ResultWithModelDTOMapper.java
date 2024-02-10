package com.happyfxmas.erdbsystem.modules.tasks.api.mapper;

import com.happyfxmas.erdbsystem.modules.ermodels.api.dto.response.ModelDetailDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.TeacherDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.TaskDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.response.ResultWithModelDTO;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Result;
import lombok.NonNull;

public class ResultWithModelDTOMapper {

    public static ResultWithModelDTO makeDTO(@NonNull Result result,
                                             @NonNull ModelDetailDTO modelDetailSourceDTO,
                                             @NonNull ModelDetailDTO modelDetailResultDTO,
                                             @NonNull TaskDTO taskDTO,
                                             TeacherDTO teacherDTO) {
        return ResultWithModelDTO.builder()
                .id(result.getId())
                .mark(result.getMark())
                .createdAt(result.getCreatedAt())
                .updatedAt(result.getUpdatedAt())
                .modelDetailSourceDTO(modelDetailSourceDTO)
                .modelDetailResultDTO(modelDetailResultDTO)
                .taskDTO(taskDTO)
                .teacherDTO(teacherDTO)
                .build();
    }
}
