package com.happyfxmas.erdbsystem.modules.tasks.api.mapper;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.StudentDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.TeacherDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PersonDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.PositionDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.StudentDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.api.mapper.TeacherDTOMapper;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Person;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.TaskDTO;
import com.happyfxmas.erdbsystem.modules.tasks.api.dtos.response.ResultWithTaskDTO;
import com.happyfxmas.erdbsystem.modules.tasks.store.models.Result;
import lombok.NonNull;

import java.util.List;

public class ResultWithTaskDTOMapper {

    public static ResultWithTaskDTO makeDTO(@NonNull Result result,
                                            @NonNull TaskDTO taskDTO,
                                            @NonNull StudentDTO studentDTO,
                                            TeacherDTO teacherDTO) {
        return ResultWithTaskDTO.builder()
                .id(result.getId())
                .mark(result.getMark())
                .createdAt(result.getCreatedAt())
                .updatedAt(result.getUpdatedAt())
                .taskDTO(taskDTO)
                .studentDTO(studentDTO)
                .teacherDTO(teacherDTO)
                .build();
    }
}
