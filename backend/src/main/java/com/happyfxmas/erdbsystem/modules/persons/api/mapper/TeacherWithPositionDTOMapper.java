package com.happyfxmas.erdbsystem.modules.persons.api.mapper;

import com.happyfxmas.erdbsystem.modules.persons.api.dto.PositionDTO;
import com.happyfxmas.erdbsystem.modules.persons.api.dto.response.TeacherWithPositionDTO;
import com.happyfxmas.erdbsystem.modules.persons.store.models.Teacher;
import lombok.NonNull;

public class TeacherWithPositionDTOMapper {
    public static TeacherWithPositionDTO makeDTO(@NonNull Teacher teacher,
                                                 @NonNull PositionDTO positionDTO) {
        return TeacherWithPositionDTO.builder()
                .id(teacher.getId())
                .positionDTO(positionDTO)
                .build();
    }
}
